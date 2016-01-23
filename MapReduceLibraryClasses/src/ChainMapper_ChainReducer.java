



public class ChainMapper_ChainReducer {
	// From Documentation
// Chain Mapper class Allows to use Multiple Mapper classes within  a single Map task
// Chain Reducer class Allows to chain multiple Mapper classes after a Reducer within the Reducer task.
// M+ R M* - is achieved using the chain Mapper and the Chain Reducer classes.
//IMP- No need to specify the output key/value classes for the chainReducer, this is done by setReducer or addMapper for the last element in the chain.
//Care has to be taken when creating  chains that the key/value output by a Mapper are valid for the following Mapper in the chain.

	//First Mapper - SplitMapper - splits the line
	//Second Mapper- LowerCaseMapper-convert all the words to lower case
	//Reducer - Reducer, word count
	//Mapper after reducer - reverses the string
	
	
}
