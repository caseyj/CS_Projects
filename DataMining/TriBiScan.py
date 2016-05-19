'''
Title: TriBiScan.py
Author: John B. Casey jbc4988@rit.edu
Description:
Called By:
'''
import sys
from operator import itemgetter

'''
Creates a dictionary counting the N-grams of a given string
The dictionary acts as a count sort and the dictionary is 
easily passed from function to function.
If a string is too short to form an N-Gram of size n,
	the function returns a dictionary with a single key value
	pair of {dict[smallKey]:1}
INPUTS:
	string: a string for N-Gramization
	size_of_grams: how large the N-Grams are
RETURNS:
	A dictionary of frequency counts for all N-Grams
'''
def DictWord(string, size_of_grams):
	#the dictionary containing N-gram counts for the given string
	graphs_words = dict()
	#where we start(usually zero, could be weird)
	start_dex = 0
	#set as the first edge, will be incremented
	last_now = size_of_grams
	#check if its too short to run and return whatever
	#also replace and remove any newlines
	string = string.rstrip('\n')
	if len(string) < size_of_grams:
		string = "^" + string + "$"
		graphs_words[string] = 1
		return graphs_words
	#loop through straaaang
	while last_now<=len(string):
		stringer_bell = string[start_dex:last_now] 
		
		#if its the beginnning of a word add a karat to the begin
		if start_dex == 0:
			stringer_bell = "^" + stringer_bell

		#if its the end of a word add a dolla dolla billz
		if last_now == len(string):
			stringer_bell = stringer_bell + "$"#just like stringer
		
		#if its in the dictionary, just increment it
		if stringer_bell in graphs_words:
			graphs_words[stringer_bell] += 1
		#otherwise, create a new key and set it to 1
		else:
			graphs_words[stringer_bell] = 1
		#increment the string indicies, because goddamn this shit isnt hard
		start_dex+=1
		last_now+=1
	return graphs_words


'''
Creates and returns a dictionary of N-gram counts for a given list of strings
This is once again, countsort for N-grams but for an entire LIST of strings
Inputs:
	list_of_strings: this is self explanitory, its a list of strings
	size_of_grams: the max size of N-grams for this iteration
Returns:
	A dictionary of N-Gram counts
'''
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

'''
Creates a dictionary of N-Grams that contain a given pattern
called by var = get_matching(dictionary_of_N_grams, string_of_pattern_to_match)
This helped easily find matching cases for:
	What character starts the most words?
	How many times is the N-Gram "te" seen?
Inputs:
	dicto: a dictionary of N-Grams and their counts
	pattern: the pattern needed to match, which N-grams could feature, maybe
Returns:
	A smaller dictionary and the counts which match the given pattern
'''
def get_matching(dicto, pattern):
	#returnable dictionary initiatied and list of keys found
	keyList = dicto.keys()
	matching = dict()
	#loop through keys
	for i in keyList:
		#if the pattern is in the key
		if pattern in i:
			#add the key and it's count to the dictionary we return
			matching[i] = dicto[i]
	#return our results
	return matching



'''
with open(sys.argv[1], 'rb') as inpu:
	listicle = inpu.readlines()
'''
#Bi_grams = (DictCombine(listicle, 2))
#print Bi_grams
#print Bi_grams["'a$"]
#print Bi_grams.keys()
#print find_Most(listicle, 2)[1]

#grams = DictCombine(listicle, sys.argv[2])

'''
while True:
	i = "\"" + raw_input("Your request here: ")+"\""
	print grams[i]
'''
#print DictWord("hello", 2)
#print DictCombine(["the", "quick", "sly", "brown", "fox", "quickly", "to", "a"], 3)
