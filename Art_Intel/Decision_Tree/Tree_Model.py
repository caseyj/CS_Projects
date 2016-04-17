
import sys
class Tree():
    
    def __init__(self):
        self.treemodel = None
    
    def train(self,trainData):
        #Attributes/Last Column is class
        self.createTree(trainData)
    
    def createTree(self,trainData):
        self.treemodel= TreeMaker(trainData, 5)
        #create the tree

    def predict(self,testData):
        #testData does not have the class column
        #returns list of the predicted output
        return []
    def Accuracy(self,testData):
        #Last Column comtains the class
        pre = self.predict(testData[:-1])
        acc =len([1 for i,a,p in enumerate(zip(testData[-1],p)) if a==p]) // len(p)
        return acc
    
    def ConfusionMatrix(self,TestData):
        #print confusion Matrix 
        #last column has the class value
        pre = self.predict(testData[:-1])


'''
Finds and returns classes of a list of a given list of datapoints
    once the target index is given
data->the data set given as a list
targetIndex->the index of the classification variable
returns->a dictionary containing counts of classes
'''
def classContent(data, targetIndex):
    #init a new dictionary
    classDict = dict()
    #loop through all rows
    for i in range(0,(len(data)-1)):
        #if we have already seen the class then increment
        #print data[i][targetIndex]

        if data[i][targetIndex] in classDict:
            print "work" 
            print i
            classDict[data[i][targetIndex]] = classDict[data[i][targetIndex]] + 1
        #otherwise create a new instance in the dictionary and set it to 1
        else:
            classDict[data[i][targetIndex]] = 1
    return classDict

'''
Counts and returns the sorted instances of a list of data
data->the data set given as a list
targetIndex->the index of the target values variable
returns->a list of sorted data
'''
def CountSort(data, targetIndex):
    classed = classContent(data, targetIndex)
    datas = list(classed.keys())
    return sorted(datas)

'''
Finds and returns the predominant class of a list of datapoints
    once the target index is given
data->the data set given as a list
targetIndex->the index of the classification variable
returns->the target variable
'''
def PredomClass(data, targetIndex):
    #init a new CC dictionary
    classDict = classContent(data, targetIndex)
    #list the values
    v = list(classDict.values())
    #list the keys
    k = list(classDict.keys())
    #return the class with the highest number of seen instances
    return k[v.index(max(v))]

'''
Generates the split dataset
'''
def GenSplit(data, splitterVal, splitterDim):
    #create two new lists to store data in
    splitList1 = list()
    splitList2 = list()
    #iterate once through all of the data
    for i in range(0,len(data)-1):
        #if data at the split location is greater than the splitterVal
        ##append to list of the larger values
        if data[i][splitterDim] >= splitterVal:
            splitList2.append(data[i])
        #otherwise append to the list of the smaller values
        else:
            splitList1.append(data[i])
    #create an empty list and add the smaller values first, larger values second
    returner = list()
    returner.append(splitList1)
    returner.append(splitList2)
    return returner


'''
Computes and returns the GINI for a single node for a split
'''
def Gini(data, targetIndex):
    c = classContent(data, targetIndex)
    vals = list(c.values())
    summa = sum(vals)
    collect = float(0)
    for i in vals:
        b = float(i)/summa
        b = b**2
        collect = float(collect) + b
    return float(1) - float(collect)


'''
Computes the Weighted GINI Index for a given split point
'''
def WGINI(data, targetIndex, splitterVal, splitterDim):
    #generate our split data
    Listicle = GenSplit(data, splitterVal, splitterDim)
    #class contents into two separate variables
    SP1 =  classContent(Listicle[0], targetIndex)
    SP2 = classContent(Listicle[1], targetIndex)
    #grab the values
    vals1 = list(SP1.values())
    vals2 = list(SP2.values())
    #calculate weighted mixed GINI
    w = (sum(vals1) / (len(data)))*Gini(vals1, targetIndex)
    w = w + (sum(vals2) / (len(data)))*Gini(vals2, targetIndex)
    return w

'''
iterative algorithm, that will find the best split point at a given decision node
iterates through each given dimension to find where the best split will be
returns a list with the first index the weighted mixed GINI, the second index is the split dimension
'''
def threshold(data, targetIndex, numberOfVars):
    #set the split point to infinity
    splitPoints = sys.maxint
    #no dimension selected to split on yet
    splitDim = -1
    #create a list of dictionaries for the values of each dimension
    
    #iterate through each dimension we are concerned about
    for i in range(1,numberOfVars):
        #iterate over each of the values in the current dimension
        Ldict = CountSort(data, i)
        for j in range(0, len(Ldict)):
            #if the weighted GINI is min, set this as the split criteria
            mG = WGINI(data, targetIndex, Ldict[j], i)
            if mG<splitPoints:
                splitPoints = mG
                splitDim = i
    report = list()
    report.append(splitPoints)
    report.append(splitDim)
    return report

'''
'''
class decisionNde():
    def __init__(self, className, data):
        self.classy = className
        self.dataset = data
        self.WhoAMI = 's'

    def WhoAMI(self):
        return self.WhoAMI

'''
'''
class spltNde():
    def __init__(self, splitValue, splitD):
        self.split = splitValue
        self.splitDim = splitDim
        self.left = None
        self.right = None
        self.WhoAMI= 'd'

    def WhoAMI(self):
        return self.WhoAMI


'''
returns the root node for a given Tree_Model dataset
'''
def TreeMaker(data, targetIndex):
    currentWeight = Gini(data, targetIndex)
    if currentWeight <= 0.1:
        return decisionNde(PredomClass(data, targetIndex), data)
    else:
        thresh = threshold(data, targetIndex, 4)
        root = spltNde(thresh[0], thresh[1])
        Listicle = GenSplit(data, thresh[0], thresh[1])
        root.right = TreeMaker(Listicle[0], targetIndex)
        root.left = TreeMaker(Listicle[1], targetIndex)
        return root
    return null
