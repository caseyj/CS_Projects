
##################
#Title: DBScan
#Author:John Casey
#Language: R
#Description: The methods and functins associateed with generating and plotting
#   DBScan
#Usage: Load the following functions into R in order to begin using DBScan
##################

##################
#Finds the euclidean distance between two equally sized vectors
##v1-the first vector
##v2-the second vector
##l-the number of attributes that are being used to calculate distance
##################
euclidean<-function(v1, v2, l){
  #start at 0, its a good place
  ecl<- 0
  for(i in range(1:l)){
    #ecl distance = ecldistance + the square of the negative sum of the attributes at
    ##position i
    ecl[1]<-ecl[1] + ((v1[[i]]-v2[[i]])^2)
  }
  #return the square root of these 
  return(sqrt(ecl))
}

##################
#Will output a K-Dists LIST
#data->the dataset to get distances between
#ktar->the Kth closest point for each point
#returns-> a vector of distances between points
##################
kDist<-function(data, ktar, size){
  #initialize a new empty list
  vecto<-vector(mode = "numeric")
  #loop through everyone once
  for(i in 1:nrow(data)){
    #initialize a new vector
    vego<-vector(mode = "numeric")
    #loop through the other data points and check for euclidean dist
    for(j in 1:nrow(data)){
      if(i != j){
        #add that distance to the vector
        vego<-c(vego,euclidean(v1 = data[i,], v2 = data[j,], l = size))
      }
    }
    #sort and add vector to the list of vectors
    sr<-sort(vego)
    vecto<-c(vecto, vego[ktar])
  }
  return(vecto)
}

##################
#Will create a plot of points for distances between a start and end value
#data->the dataset to get distances between
#ktar->the Kth closest point for each point
#returns-> a vector of distances between points
##################
kDistPlot<-function(data, start, end){
  vec<-kDist(data, start)
  points(sort(vec), col = colors[1], xlab = "index", ylab = "Distance betweeen star and kth closest neighbor")
  colors = c("red", "green","blue","magenta", "black", "gray", "brown", "orange", "pink", "cyan")
  for(i in (start+1):end){
    vec<-kDist(data, i)
    points(sort(vec), col = colors[i])
  }
}

##################
#creates and adds a classification for every data point,
# based upon a selected epsilon
#data->the dataset we wish to use, with a column with distances to kth 
# closest member
#Epsilon->The inflection point chosen
#vectorrayIndex->the index of the distance column
#returns->a vector of 
DBClassify<-function(data, Epsilon, vectorrayIndex){
  #empty character vector init
  vecto = vector(mode = "character")
  #iterate through the rows
  for(i in (1:nrow(data))){
    #if we find a core point, turn this to true so we dont trigger the other if
    hit = FALSE
    #if core point
    if(data[i,vectorrayIndex]<Epsilon){
      #add a C to the character vector and hit becomes true
      vecto = c(vecto, 'c')
      hit = TRUE
    }
    #otherwise
    if(!hit){
      #if its equal to our selected epsilon
      if(data[i,vectorrayIndex]==Epsilon){
        #add a B to the character vector
        vecto = c(vecto, 'b')
      }
      #otherwise
      else{
        #add an N, its noise
        vecto = c(vecto, 'N')
      }
    }
  }
  #return that vector
  return(vecto)
}

#finds all neighbors of a given dataPoint and returns their indicies in a vector
checkArea<-function(Epsilon, dataSet, dataIndex, rawSize){
  #init new empty vector
  vecto<-vector(mode = "numeric")
  #loop through data points
  for(i in 1:nrow(dataSet)){
    #find distance between current row, and comparison row
    dist = euclidean(v1 = dataSet[i,], v2 = dataSet[dataIndex,], l = rawSize)
    #if the distance is less than epsilon, and not the same dataPoint
    if(dist<=Epsilon && dist!=0){
      #add it to the returning vector
      vecto<-c(vecto, i)
    }
  }
  return(vecto)
}

#returns an entire cluster and vetted data set via a list
FindCluster<-function(index, neighborhood, data, rawSize, Epsilon, MinPoints, clusterNum){
  #print(neighborhood)
  newClust<-vector(mode = "numeric")
  newClust<-c(newClust, index)
  data[index,]$member = clusterNum
  #begin looping through neighbors
  for(i in 1:length(neighborhood)){
    #check if seen
    curNabe<-neighborhood[i] 
    if(data[curNabe,]$seen==0){
      #set it as seen
      data[curNabe,]$seen = 1
      #find its neighbors and add them to neighborlist
      nabes<-checkArea(Epsilon = Epsilon, dataSet = data, rawSize = rawSize, dataIndex = i)
      #if the point is a viable corePt
      if(length(nabes)>=MinPoints){
        #iterate through each neighbor and add uniques to the neighborhood
        # queue
        for(j in 1:length(nabes)){
          if(is.element(el = nabes[j], set = neighborhood) == FALSE){
            neighborhood<-c(neighborhood, nabes[i])
          }
        }
        #check if in cluster already
        if(data[neighborhood[i],]$member==0){
          #add clusterNumber to dataSet
          data[neighborhood[i],]$member = clusterNum
          #add row to cluster
          newClust<-c(newClust, curNabe)
        }
      }
      else{
        data[i,]$class = 'N'
        data[i,]$seen = 1
      }
    }
    
  }
  #return both the new data and the cluster
  ret<-list()
  ret<-c(ret, list(newClust))
  ret<-c(ret, list(data))
  return(ret)
}

#Runs DBScan algorithm, with a given epsilon
# Assumes data already has been run through a classification routine
DBScanner<-function(data, Epsilon, rawSize, minPts=4){
  clustering = list()
  clusterNumber = 1
  #set seen as 0, member as 0, and character as empty
  data$seen<-vector(mode = "numeric", length = nrow(data))
  data$class<-vector(mode = "character", length = nrow(data))
  data$member<-vector(mode = "numeric", length = nrow(data))
  #loop through the data
  for(i in 1:nrow(data)){
    #if we havent seen this point yet
    neighborhood<-vector(mode = "numeric")
    if(data[i,]$seen==0){
      #mark as seen
      data[i,]$seen <-1
      #get its immediate neighbors
      neighborhood<-checkArea(Epsilon = Epsilon, dataSet = data, rawSize = rawSize, dataIndex = i)
      #if it doesnt really have enough neighbors, its noise
      if(length(neighborhood)<minPts){
        data[i,]$class = 'N'
        data[i,]$seen = 1
        
      }
      #otherwise
      else{
        #generate new cluster here!
        v<-FindCluster(index = i, neighborhood = neighborhood, data = data, Epsilon = Epsilon, MinPoints = minPts, clusterNum = clusterNumber, rawSize)
        #if the cluster is not larger than the minimum number of points required, igore 
        #if(length(v[[1]])>=minPts){
          #otherwise, add it to the clusterList!
          clustering<-c(clustering, list(v[[1]]))
          clusterNumber = clusterNumber+1
        #}
        data = v[[2]]
      }
    }
  }
  return(clustering)
}

#Creates a 3d Scatter plot of the DB Scan results
plotDBscan<-function(vectorList, data){
  #a vector of the namespace colors
  colors = c("red", "green","blue","magenta", "black", "gray", "brown", "orange", "pink", "cyan")
  #lets add the first cluster
  firstSet<-vectorList[[1]]
  points3D(x = data[firstSet[1],1], y = data[firstSet[1],2], z = data[firstSet[1],3], col=colors[1], xlim=c(0,10), ylim=c(0,10), zlim=c(0,10), main = "Clusters")
  for(i in 2:length(firstSet)){
    points3D(x = data[firstSet[i],1], y = data[firstSet[i],2], z = data[firstSet[i],3], col=colors[1],add=TRUE)
  }
  #loop through the rest of the clusters and add them to the plot
  for(i in 2:length(vectorList)){
    for(h in 1:length(vectorList[[i]])){
      points3D(x = data[vectorList[[i]][h],1], y = data[vectorList[[i]][h],2], z = data[vectorList[[i]][h],3], col=colors[i%%length(colors)], add=TRUE)
    }
  }
}
