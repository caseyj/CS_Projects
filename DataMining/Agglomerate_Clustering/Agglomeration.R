##################
#Title: Agglomeration.r
#Author: John B. Casey
#Language: R
#Purpose: a means to use agglommeration clustering on a given dataset
#
##################

##################
#Finds the euclidean distance between two equally sized vectors
##################
euclidean<-function(v1, v2, length){
  ecl<- 0
  for(i in range(1:length)){
    ecl[1]<-ecl[1] + ((v1[i]+v2[i])^2)
  }
  
  return(sqrt(ecl))
}

