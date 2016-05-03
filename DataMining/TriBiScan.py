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
	#also replace and remove any newlines
	string = string.replace("'", "\'", len(string))
	string = string.rstrip('\n')


	if len(string) < size_of_grams:
		string = "^" + string + "$"
		graphs_words[string] = 1
		return graphs_words
	#loop through straaaang
	while last_now<=len(string):
		#named for the wire character, if you havent seen the 
		#wire you need to seriously get on that. amazing show
		stringer_bell = string[start_dex:last_now] 
		
		#if its the beginnning of a word add a karat to the begin
		if start_dex == 0:
			stringer_bell = "^" + stringer_bell

		#if its the end of a word add a dolla dolla billz
		if last_now == len(string):
			stringer_bell = stringer_bell + "$"#just like stringer
		
		#if its in the dictionary, just increment it
		print stringer_bell
		if stringer_bell in graphs_words:
			graphs_words[stringer_bell] += 1
		#otherwise, create a new key and set it to 1
		else:
			graphs_words[stringer_bell] = 1
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

def find_Most(listicle, size_of_grams):
	scan = DictCombine(listicle, size_of_grams)
	Count_Sorted_Dict = sorted(scan)
	return Count_Sorted_Dict







with open(sys.argv[1], 'rb') as inpu:
	listicle = inpu.readlines()

Bi_grams = (DictCombine(listicle, 2))
print Bi_grams
#print Bi_grams["'a$"]
#print find_Most(listicle, 2)[1]





#print DictWord("hello", 2)
#print DictCombine(["the", "quick", "sly", "brown", "fox", "quickly", "to", "a"], 3)
