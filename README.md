# Bitopiary

A recreational esoteric language which sacrifices everything to be consistent but doesn't dare sacrifice enough of everything

## Memory model

Bitopiary uses a grid of bits to store its data and the program itself. Each program has exactly one 'bitgrid'. The bits are modified and interpreted with help of concepts called 'caret' and 'read head'. 

The carets are analogous to carets in a ordinary word processor, they mark where on the grid data will be read or stored. 

Starting from the caret the read-head decides which bits will be read and interpreted as an integer or character (floats: Todo). The readhead marks a rectangular region which is read left to right, top to bottom. See image below for an example:(todo)

## Execution

The running code and the data it operate on is not separated, it is all stored on the bitgrid. The source is converted to an 'ExecutionTrack', there can be multiple executiontracks running in the same program. 

An ExecutionTrack is an entity which contains:

1. One execution caret - Where current instructions are read
2. Execution direction - Which direction(right, left, up, down) the execution caret moves on the 2dimensional grid
3. Memory carets - Which marks where instructions will get their input
4. The read head configuration - which interprets the bits into appropriate data structures such as Integers or Characters
5. States for the various instructions - E.g arithmetic operations such as addition and multiplication has their own memory in addition to the grid (Possibly getting moved to the grid later)

Note that an executiontrack does not contain its own bitgrid, it operates on the one shared by all execution tracks in the program. This allows you to organize your code into execution tracks and then have them interact very easily. 

The source code of a simple programs only execution track by default goes to the first row of the grid and is read from left to right. When using multiple execution tracks they take turns executing their instructions and do so in a synchronized fashion until they have all finished. For more details see instruction section


## Examples

HelloWorld:

`HelloWorldH[:.>]`

Print Fibbonacci numbers

`1$>;1[\:+\+\+]`

## Instructions: Complete instructionset - Todo
