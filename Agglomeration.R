##################
#Title: Agglomeration.r
#Author: John B. Casey
#Language: R
#Purpose: a means to use agglommeration clustering on a given dataset
#Call: \>:rscript Agglomeration.r [dataFile] [number of suspected clusters]
##################

args<-commandArgs(TRUE)

if(length(args) <2){
  
  print("Must be called: ")
  stop(">:rscript Agglomeration.r [dataFile] [number of suspected clusters]")
}

fileName <- args[1]
clusters<- as.numeric(args[2])

c1<-read.csv(file = fileName)


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
genClust<-function(toBeClustered){
  clust<-cluster(IDList=toBeClustered[[1]], Centroid = toBeClustered[2:length(toBeClustered)], Clustered = toBeClustered[2:length(toBeClustered)])
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
    gclu<-genClust(RowsofData[i,])
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
#Merges two given clusters together
#Clusters->the cluster list both clusters belong to
#c1Loc-> the number of the first cluster to be merged
#c2Loc-> the numer of the second cluster to be merged
#returns-> a modified cluster list with modified clusters, length 1 shorter
##################
merge<-function(clusters, c1Loc, c2Loc){
  #cluster 1 locations
  c1ID<-findClId(c1Loc)
  c1clus<-findClclust(c1Loc)
  #cluster 2 locations
  c2ID<-findClId(c2Loc)
  c2clus<-findClclust(c2Loc)
  
  #add cluster2's information to cluster1
  clusters[[c1ID]]<-c(clusters[[c1ID]], clusters[[c2ID]])
  clusters[[c1clus]]<-rbind.data.frame(clusters[[c1clus]], clusters[[c2clus]])
  clusters[[(c1ID+1)]]<-findCentroid(clusters[[c1clus]])
  
  #remove defunct clusters
  clusters<-clusters[-c(c2ID:c2clus)]
  #return
  return(clusters)
}

##################
#Finds the euclidean distance between two clusters
#c1->the location of the first cluster
#c2->the location of the second cluster
#returns-> the numerical euclidean distance between the two clusters
##################
clusterDist<-function(c1, c2){
  #return the result of the function call euclidean on findCentroid[1,2]
  return(euclidean(findCentroid(c1),findCentroid(c2), (length(c2)-1) ))
}

##################
#Generates a matrix for the distances between the clusters (runs in O(N^2))
#Clusters->the dataFrame of centroids all clusters feed prior to running
#returns-> an NxN matrix of the distance between each cluster and 
##every other cluster
##################
clusterMatrix<-function(clusters){
  #initialize a new, empty matrix
  newMat<-matrix(nrow = nrow(clusters), ncol=nrow(clusters), data=0)
  #iterate through every row of the data frame
  for(i in 1:nrow(clusters)){
    #vector that will be added to the next row of the matrix
    v<-vector(mode="numeric", length=length(clusters))
    #iterate now against every row of the data frame
    for(j in 1:nrow(clusters)){
      #if we are looking at the kth row and the kth col
      ##we assume these are referring to the distance between an object
      ##and itself so we set that to the maximum integer in order to disregard
      ##this box during final analysis
      if(j==i){
        newMat[i,j]<- .Machine$integer.max
      }
      #otherwise we are looking at the distance between two distinct objects
      ##and we are interested in that and store it at the [i,j]th location
      else{
        newMat[i,j]=clusterDist(clusters[i,], clusters[j,])
      }
    }
  }
  #return the full matrix
  return(newMat)
}

##################
#Finds the locations of the lowest values in a matrix
#LocationMatrix->the matrix we wish to extract the minimums from
#returns-> a matrix of the row and col coordinates of the matrix's minimum values
##################
locator<-function(LocationMatrix){
  #the call used to find the coordinates of the smallest values of the matrix
  ind<-which(LocationMatrix == min(LocationMatrix), arr.ind = TRUE)
  return(ind)
}
##################
#Generates a dataFrame from a given list of clusters from their centroids
#listicle->the list of clusters 
#length->the number of clusters we must loop through
#namesies->the names of the variables for the dataframe
#returns-> a dataframe of the centroids of the clusters
##################
frameIt<-function(listicle, length, namesies){
  #create an empty data frame we wish to restore
  dF<-data.frame(check.names = FALSE)
  #iterate through the entirety of the list
  for(i in 1:length){
    #add a row to the dataframe of the ith centroid 
    dF<-rbind.data.frame(dF, listicle[[findClcent(i)]])
    #force names otherwise we get serious errors
    dF<-setNames(dF, namesies)
  }
  #return
  return(dF)
}

##################
#Using a given location list of the smallest values of a matrix
##return the 2 smallest clusterings for merging
#Clusters->the cluster list all clusters belong to
#mima->the coordinates of the minimum values of the matrix, corresponding to 
##the clusters to be merged
#returns-> the best coordinates for merging
##################
determineSMClu<-function(Clusters, mima){
  #create 2 new vectors, minSize will hold the vector sizes before addition
  ##mimmer will hold the sizes after addition
  minSize<-vector(mode="numeric", length = length(mima))
  mimmer<-vector(mode="numeric", length = length(mima)/2)
  #loop through mima
  for(i in 1:length(mima)){
    #minSize will equal how large each cluster is
    minSize[i]<-nrow(Clusters[[findClclust(mima[i])]])
  }
  for(i in 1:length(mima)/2){
    #add each of the sizes of the corresponding locations
    mimmer[i]<-minSize[i]+minSize[i+length(mima)/2]
  }
  #loc finds the location of the minimum in mimmer
  loc<-which.min(mimmer)
  #we store the minimums(smallest clustering) in civ and return civs sorted values
  civ<-c(mima[(loc)], mima[(loc+(length(mima)/2))])
  civ<-sort(civ)
  return(civ)
}

##################
#Generates a clustering up to the given number of intended clusters
#dataSet->the dataframe that comes from just reading in a CSV
#depth->the number of clusters we wish to have
#c2Loc-> the numer of the second cluster to be merged
#returns-> a group of clusters
##################
CLUSTERER<-function(dataSet,depth){
  #we begin by generating a list of clusters, 1 for each data point 
  ##and storing these in d
  d<-generateClusters(dataSet)
  #we also get the names of all the attributes for safe keeping
  nameser<-names(dataSet)[2:length(names(dataSet))]
  #we iterate through until the number of clusters is equal to the intended depth
  while((length(d)/3)>depth){
    #the length that we must feed to the data framer function
    l<-NROW(d)/3
    #a data frame is created from the centroid of all the clusters as this is
    ## an easy object to work with in the matrix, with fr we can move to matrices
    fr<-frameIt(listicle = d, length = l, namesies = nameser)
    #we generate a matrix of the distances between each centroid, so we can determine
    ##which two centroids should be merged
    mat<-clusterMatrix(fr)
    #We select with minMat the minimums of the matrix 
    minMat<-locator(mat)
    #there may be more than one minimum merging, this function will find the merging
    ##that results in the smallest possible merging to occur and format perfectly
    mimmer<-determineSMClu(d, minMat)
    #we merge the selected clusters and set d equal to the modified cluster list
    d<-merge(d, mimmer[1], mimmer[2])
  }
  #return your new CLUSTERED LIST!
  return(d)
}

##################
#Prints the centroids of a clustered list 
#clusteredGroup->the clusters we wish to print the centroids of
##################
printCents<-function(clusterGroup){
  #iterate through the centroids
  for(i in seq(from = 2, to = length(clusterGroup), by = 3)){
    #print the centroid
    print(clusterGroup[i])
  }
}

##################
#Prints the IDs of a clustered list with their neighbors
#clusteredGroup->the clusters we wish to print the IDs of
##################
printIDs<-function(clusterGroup){
  #iterate through the ID's
  for(i in seq(from = 1, to = length(clusterGroup), by = 3)){
    #print the ID
    print(clusterGroup[i])
  }
}



CLUSTERER(c1, clusters)