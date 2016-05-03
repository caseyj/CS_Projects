'''
Title: TriBiScan.py
Author: John B. Casey jbc4988@rit.edu
Description:
Called By:
'''


def DictWord(string, size_of_grams):
	#the dictionary containing N-gram counts for the given string
	graphs_words = dict()
	#where we start(usually zero, could be weird)
	start_dex = 0
	#set as the first edge, will be incremented
	last_now = size_of_grams
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
	superDict = dict()
	for i in list_of_strings:
		single = DictWord(i, size_of_grams)
		SingleKeys = list(single.keys())

print DictWord("hello", 2)
