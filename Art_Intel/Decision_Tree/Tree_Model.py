

class Tree():
    
    def __init__(self):
        self.treemodel = None
    
    def train(self,trainData):
        #Attributes/Last Column is class
        self.createTree(trainData)
    
    def createTree(self,trainData):
        self.treemodel=None
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
    for i in data:
        #if we have already seen the class then increment
        if i[targetIndex] as key in classDict:
            classDict[key] = classDict[key] + 1
        #otherwise create a new instance in the dictionary and set it to 1
        else:
            classDict[i[targetIndex]] = 1
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
    return datas.sort()

'''
Finds and returns the predominant class of a list of datapoints
    once the target index is given
data->the data set given as a list
targetIndex->the index of the classification variable
returns->the target variable
'''
def PredomClass(data, targetIndex):
    #init a new dictionary
    classDict = dict()
    #loop through all rows
    for i in data:
        #if we have already seen the class then increment
        if i[targetIndex] as key in classDict:
            classDict[key] = classDict[key] + 1
        #otherwise create a new instance in the dictionary and set it to 1
        else:
            classDict[i[targetIndex]] = 1
    #list the values
    v = list(classDict.values())
    #list the keys
    k = list(classDict.keys())
    #return the class with the highest number of seen instances
    return k[v.index(max(v))]




'''
iterative algorithm, that will find the best split point at a given decision node
iterates through each given dimension to find where the best split will be
returns a list with the first index the weighted mixed GINI, the second index is the split dimension
'''
def threshold(data, targetIndex):
    splitPoints = list(sys.maxint)
    splitDim = -1
    itera = list()
    itera.append(CountSort(data, 0))
    itera.append(CountSort(data, 1))
    itera.append(CountSort(data, 2))
    itera.append(CountSort(data, 3))
    itera.append(CountSort(data, 4))
    for i in range(0,len(itera)):
        for j in range(0, len(itera[i])):
            mG = WGINI(data, targetIndex, itera[i][j], i)
            if mG<splitPoints:
                splitPoints = mG
                splitDim = i
    report = list()
    report.append(min(splitPoints))
    report.append(splitPoints.index(min(report)))
    return report
'''
Computes and returns the GINI for a single node for a split
'''
def Gini(data, targetIndex):
    vals = list(classContent(data, targetIndex).values())
    summa = sum(vals)
    for i in vals:
        i = i/summa
        i = i**2
    summa = sum(vals)
    return 1 - summa


'''
Computes the Weighted GINI Index for a given split point
'''
def WGINI(data, targetIndex, splitterVal, splitterDim):
    #ClassCounts = classContent(data, targetIndex)
    splitList1 = list()
    splitList2 = list()
    for i in range(1:len(data)):
        if i[splitterDim] >= splitterVal:
            splitList2.append(i)
        else:
            splitList1.append(i)
    SP1 =  classContent(splitList1, targetIndex)
    SP2 = classContent(splitList2, targetIndex)
    vals1 = list(SP1.values())
    vals2 = list(SP2.values())
    w = (sum(vals1) / (len(data))*Gini(vals1)
    w = w + (sum(vals2) / (len(data))*Gini(vals2)
    return w

'''
'''
class decisionNde():
    def __init__(self, className):
        self.classy = className
        self.WhoAMI = 's'

    def WhoAMI(self):
        return self.WhoAMI

'''
'''
class spltNde():
    def __init__(self, splitValue, splitDim):
        self.split = splitValue
        self.splitDim = splitDim
        self.left = None
        self.right = None
        self.WhoAMI= 'd'

    def WhoAMI(self):
        return self.WhoAMI

    def setBranch(self, tYpe, side, data):
        if(side == 'r'):
            if(tYpe == 's'):
                #some method to generate the splitting criteria
                #self.right = spltNde()
            else:
                #some method to generate which class this belongs to
                #self.right = decisionNde()

        pass

