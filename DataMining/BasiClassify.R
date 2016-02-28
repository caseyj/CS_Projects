######
#Author: John Casey
#Title: BasiClassify.r
#Language: R

args<-commandArgs(TRUE)

if(length(args) <5){

	print("Must be called: ")
	stop(">:rscript BasiClassify.r [dataFile] [data_Fileof_Unclassified_Data] [binSize integer] [user defined min] [user defined max]")
}

fileName <- args[1]
fileTwo <- args[2]
binSize <-as.numeric(args[3])
min<- as.numeric(args[4])
max<- as.numeric(args[5])

c1<-read.csv(file = fileName)
c2<-as.numeric(c1$SPEED)
c2<-na.omit(c2)

c3<-cut(c2, breaks = seq(min,max, by=binSize), right= FALSE)
hist(c2, breaks = seq(min,max, by=binSize), right=FALSE)

#cut frequencies
c4<-summary(c3)

#histogram data and break down
c5<-hist(c2, breaks = seq(min,max, by=binSize), right=FALSE)

#lenC4 for easy access to how many bins exist
lenC4<-length(c4)

#histMids is for easy access to the medians for each bin
histMids<-c5$mids

#what we hope to use as a comparative value set to R's maximum 
bestMixedVar<- .Machine$integer.max

#the best threshold
bestMixedVar[2]<- .Machine$integer.max

#relative density on histogram/probability
density<- c5$density


mixedVar<-0

#this is a loop in order to conduct OTSU's method
for(i in 1:(lenC4-1)){
	#weights on the left side of the threshold
	lW <- sum(density[1:i])
	#control flow for R usage, if i=1 then lVar= 0
	#lVar is the variance of all data below the threshhold
	if(i-1>0){
		lVar <- sd(c4[1:i])^2
	}
	else{
		lVar<- 0
	}
	
	#weights on the right side of the current threshold
	rW <- sum(density[(i+1):lenC4]) - lW

	#control flow for R usage, if i=1 then rVar= 0
	#rVar is the variance of all data below the threshhold
	if(((i+1)-lenC4) <0){
		rVar <- sd(c4[(i+1):lenC4])^2
	}
	else{
		rVar<- 0
	}
	#the variance score of the current threshhold
	mixedVar[1] <- (lW*lVar)+(rW*rVar)

	#if mixedVar less than our best, then we have a new best!
	if((mixedVar[1] <= bestMixedVar[1])){
		bestMixedVar[1] <- mixedVar[1]
		bestMixedVar[2] <- i
	}
}
hist(c2, breaks=seq(38,80, by=.5), right=FALSE, col=heat.colors(90))
abline(v=c5$breaks[bestMixedVar[2]], col="blue")


library(ggplot2)

binCount1<-vector(mode="numeric", length=length(c2))
binCount0<-vector(mode="numeric", length=length(c2))

####
#the following for loop determines counts for whether a 
#	driver was reckless or not in each bin
for(i in (1:length(c2) ) ) {

	currentSpeed = c2[i]
	currentbinary = c1$RECKLESS[i]
	#the cut number that the current speed belongs to
	inCut<-floor(2*(currentSpeed-38))+1
	
	#if a 1 add it to that cut's count for true values
		#binCount1; otherwise add it to that cut's count
		#for false values in binCount0
	if(currentbinary ==1){
		binCount1[inCut]<-binCount1[inCut] + 1
	}
	else{
		binCount0[inCut]<-binCount0[inCut] + 1
	}

}

#BMC[1] is the number misclassified at a given threshold
bestMisClass<-.Machine$integer.max

#BMC[2] is the threshold number
bestMisClass[2]<-.Machine$integer.max

#number of wrong classifications in each threshold
nW<-vector(mode="numeric")

#the number of false alarms in each threshold
fa<-vector(mode="numeric")
#the number of true positives in each threshold
tp<-vector(mode="numeric")

for(i in (1:(lenC4-1))){
	na_miss<-sum(binCount1[1:i])
	na_fa <- sum(binCount0[(i+1):lenC4])
	n_wrong<-na_miss+na_fa

	#add the number wrong to the nW vector
	nW<-c(nW, n_wrong)
	
	#add the number of false alarms to the fa vector
	fa<-c(fa, na_fa)

	#add the number of true positives to the tp vector
	tp<-c(tp, sum(binCount0[1:i]))

	#if the number of wrong hits is less than or equal to the best misclassification
		##execute the loop
	if(n_wrong<=bestMisClass[1]){
		#BMC1<-the number wrong
		bestMisClass[1]<-n_wrong
		
		#BMC2<-the threshold number
		bestMisClass[2]<-i
		
		#number of hits below the threshold
		bestMisClass[3]<-na_miss
		
		#number of false alarms above threshold
		bestMisClass[4]<-na_fa
	}
}
fa<-fa/sum(binCount0)
tp<-tp/sum(binCount1)

#the fractions of how many are misclassified
frac <- nW/(length(c2)-bestMisClass[1])
#the value at which all below are the 
ThreshLvl<-c5$breaks[bestMisClass[2]+1]

#the data to be classified based upon the created model above
c6<-read.csv(file=fileTwo, header=FALSE)
c7<-as.numeric(c6$V1)


speeders<-vector(length=length(c7))
#a loop in which we will classify the data based upon the model generated above
for(i in (1:length(c7))){
	if(c7[i]>=ThreshLvl){
		speeders[i]<-1
	}
	else{
		speeders[i]<-0
	}	
}
#write which ones are speeding to a CSV(hardcoded for HW assignment)
write.csv(speeders, "HW03_Casey_John_CLASSIFICATIONS.csv")
print("Classified speeders are in file named: HW03_Casey_John_CLASSIFICATIONS.csv")
sprintf("The best threshold is %s - %s and the best mixed variance is %s", c5$breaks[bestMisClass[2]] ,c5$breaks[bestMisClass[2]]+binSize , bestMixedVar)

