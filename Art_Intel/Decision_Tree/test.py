import pickle
import csv
import sys
with open('model.pkl','rb') as h:
    model=pickle.load(h)

storage = list()
storage2 = list()
with open(sys.argv[1], 'rb') as inpu:
	inputData = csv.reader(inpu)
	inputData.next()
	for row in inputData:
		row[0] = float(row[0])
		row[1] = float(row[1])
		row[2] = float(row[2])
		row[3] = float(row[3])
		row[4] = float(row[4])
		storage.append(row)
with open(sys.argv[2], 'rb') as inpu:
	inputData = csv.reader(inpu)
	inputData.next()
	for row in inputData:
		row[0] = float(row[0])
		row[1] = float(row[1])
		row[2] = float(row[2])
		row[3] = float(row[3])
		row[4] = float(row[4])
		storage2.append(row)
print(model.Accuracy(storage))
model.ConfusionMatrix(storage, storage2)
