
##################
#Title: DBScan
#Author:John Casey
#Language: R
#Description: The methods and functins associateed with generating and plotting
#   DBScan
#Usage: Load the following functions into R in order to begin using DBScan
##################


##################
#The cluster data structure, keeps track of any cluster data needed
##IDList-the ID's associated with the cluster
##Centroid-the center of the cluster
##Clustered-the actual cluster data
##################
cluster<-function(IDList=0, Centroid=1, Clustered=1){
  IDs<-vector()
  nCluster<-list(
    IDs = IDList,
    cent = Centroid,
    Clust = Clustered
  )
  class(nCluster)<- append(class(nCluster),"cluster")
  return(nCluster)
}

####################################


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
#generate Cluster from raw row of data
#toBeClustered->csv derived data frame row that we wish to create a cluster from
#returns->single cluster object (single data point)
##################
genClust<-function(toBeClustered, ID){
  clust<-cluster(IDList= ID, Centroid = toBeClustered[1:length(toBeClustered)], Clustered = toBeClustered[1:length(toBeClustered)])
  return(clust)
}

##################
#generate Cluster list
#RowsofData->csv derived data frame row that we wish to create a cluster list from
#returns->list of cluster objects
##################
generateClusters<-function(RowsofData){
  #create an empy list we will use to store the clusters
  ClusterList<-list()
  #iterate through all of the data rows
  for(i in 1:nrow(RowsofData)){
    #generate a single cluster
    gclu<-genClust(RowsofData[i,], i)
    #add it to the list
    ClusterList<-c(ClusterList, gclu)
  }
  #returning objects is generally a good idea
  return(ClusterList)
}

##################
#Returns the position within the cluster list of a given cluster's ID's
#clNum->the number of the cluster, who's ID's we would like to view
#returns->returns the position of a cluster's ID based upon the number 
##of the cluster provided
##################
findClId<-function(clNum){
  c<-clNum-1
  return((c * 3)+1)
}

##################
#Returns the position within the cluster list of a given cluster's centroid
#clNum->the number of the cluster, who's centroid we would like to view
#returns->returns the position of a cluster's centroid based upon the number
##of the cluster provided
##################
findClcent<-function(clNum){
  c<-clNum-1
  return((c * 3)+2)
}

##################
#Returns the position within the cluster list of a given cluster's members
#clNum->the number of the cluster, who's member data we would like to view
#returns->returns the position of a cluster's member data based upon the number
##of the cluster provided
##################
findClclust<-function(clNum){
  return(clNum * (3) +0)
}

##################
#Returns the centroid of a cluster
#dF-> the data frame corresponding to the cluster
#returns->returns a full centroid for the attributes of a given cluster
##################
findCentroid<-function(dF){
  # initialize a new empty vector
  cent = vector()
  #iterate through the attributes
  for(i in 1:length(names(dF))){
    #make position i of cent equal to the mean of the ith attribute
    cent = c(cent, sum(dF[[i]])/nrow(dF))
  }
  #return the vector
  return(cent)
}

##################
#Finds the euclidean distance between two clusters
#c1->the location of the first cluster
#c2->the location of the second cluster
#returns-> the numerical euclidean distance between the two clusters
##################
clusterDist<-function(c1, c2){
  #return the result of the function call euclidean on findCentroid[1,2]
  return(euclidean(findCentroid(c1),findCentroid(c2), (length(c2)) ))
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
    dist<-dist = euclidean(v1 = dataSet[i,], v2 = dataSet[dataIndex,], l = rawSize)
    #if the distance is less than epsilon, and not the same dataPoint
    if(dist<=Epsilon && dist!=0){
      #add it to the returning vector
      vecto<-c(vecto, dist)
    }
  }
  return(dist)
}

#Runs DBScan algorithm, with a given epsilon
# Assumes data already has been run through a classification routine
DBScanner<-function(data, Epsilon, rawSize, classifyLoc, minPts=4){
  #remove any noise by classification
  cleanDat<-subset(data, data[[classifyLoc]]!='N')
  #generate a cluster list
  
  clustering<-generateClusters(RowsofData = cleanDat)
  for(i in 1:(nrow(cleanDat)-1)){
    #current row comparing use
    current<-cleanDat[i,]
    #loop through all 
    for(j in ((i+1):nrow(cleanDat))){
      #get the euclidean distance between the two current comp points
      dist = euclidean(v1 = cleanDat[i,], v2 = cleanDat[j,], l = rawSize)
      #if its less than or equal to Epsilon, we create an edge between the two
      if(dist<=Epsilon){
        
        idI<-findClId(clNum = i)
        idJ<-findClId(clNum = j)
        #add the ID's together
        clustering[[idI]] = c(clustering[[idI]], j)
        clustering[[idJ]] = c(clustering[[idJ]], i)
        
      }
    }
  }
  return(clustering)
}


#distills an adjacency list of cluster connections to a cluster list
reduceToClusters<-function(clustering){
  toRemove<-vector(mode = "numeric")
  
}
