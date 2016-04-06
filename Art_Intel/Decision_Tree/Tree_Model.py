

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

    def function():
        pass

