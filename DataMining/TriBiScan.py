'''
Title: TriBiScan.py
Author: John B. Casey jbc4988@rit.edu
Description:
Called By:
'''
import sys


def DictWord(string, size_of_grams):
	#the dictionary containing N-gram counts for the given string
	graphs_words = dict()
	#where we start(usually zero, could be weird)
	start_dex = 0
	#set as the first edge, will be incremented
	last_now = size_of_grams
	#check if its too short to run and return whatever
	string = string.lower()
	if len(string) < size_of_grams:
		graphs_words[string] = 1
	#loop through straaaang
	while last_now<=len(string):
		#if its in the dictionary, just increment it
		if string[start_dex:last_now] in graphs_words:
			graphs_words[string[start_dex:last_now]] += 1
		#otherwise, create a new key and set it to 1
		else:
			graphs_words[string[start_dex:last_now]] = 1
		#increment the string indicies, because goddamn this shit isnt hard
		start_dex+=1
		last_now+=1
	return graphs_words

def DictCombine(list_of_strings, size_of_grams):
	#a dictionary for every N-Gram
	superDict = dict()
	#loop through strings list
	for i in list_of_strings:
		#find the N-gram count for a single word
		single = DictWord(i, size_of_grams)
		#list of the grams
		SingleKeys = list(single.keys())
		#loop through the keylist and 
		for j in SingleKeys:
			#if its in the dictionary, add its grams
			if j in superDict:
				superDict[j] += single[j]
			#otherwise, make a new key and add those grams
			else:
				superDict[j] = single[j]
	return superDict










with open(sys.argv[1], 'rb') as inpu:
	listicle = inpu.readlines()
bi_dict = DictCombine(listicle, 2)
tri_dict = DictCombine(listicle, 3)
print bi_dict


print DictWord("hello", 2)
print DictCombine(["the", "quick", "sly", "brown", "fox", "quickly", "to", "a"], 3)
