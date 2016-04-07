

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


    
class decisionNde():
    def __init__(self, className):
        self.classy = className
        self.WhoAMI = 's'

    def WhoAMI(self):
        return self.WhoAMI

class spltNde():
    def __init__(self, splitValue):
        self.split = splitValue
        self.left = None
        self.right = None
        self.WhoAMI

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

