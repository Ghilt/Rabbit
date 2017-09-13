# Bitopiary

A recreational esoteric language which sacrifices everything to be consistent but doesn't dare sacrifice enough of everything

## Memory model

Bitopiary uses a grid of bits to store its data and the program itself. Each program has exactly one 'bitgrid'. The bits are modified and interpreted with help of concepts called 'caret' and 'read head'. 

The carets are analogous to carets in a ordinary word processor, they mark where on the grid data will be read or stored. 

Starting from the caret the read-head decides which bits will be read and interpreted as an integer or character (floats: Todo). The readhead marks a rectangular region which is read left to right, top to bottom. See image below for an example:(todo)

## Execution

The running code and the data it operate on is not separated, it is all stored on the bitgrid. The source is converted to an 'ExecutionTrack', there can be multiple executiontracks running in the same program. 

An ExecutionTrack is an entity which contains:

 - One execution caret - Where current instructions are read
 - Execution direction - Which direction(right, left, up, down) the execution caret moves on the 2dimensional grid
 - Memory carets - Which marks where instructions will get their input
 - The read head configuration - which interprets the bits into appropriate data structures such as Integers or Characters
 - States for the various instructions - E.g arithmetic operations such as addition and multiplication has their own memory in addition to the grid (Possibly getting moved to the grid later)

Note that an Executiontrack does not contain its own bitgrid, it operates on the one shared by all execution tracks in the program. This allows you to organize your code into execution tracks and then have them interact very easily.

The source code of a simple programs only execution track by default goes to the first row of the grid and is read from left to right. When using multiple execution tracks they take turns executing their instructions and do so in a synchronized fashion until they have all finished. For more details see instruction section


## Examples

HelloWorld:

`HelloWorldH[:.>]`

Print Fibbonacci numbers

`1$>;1[\:+\+\+]`

## Syntax:

Instructions in Bitopiary are one character, almost all characters are considered instructions.

There are four default input modes for instructions:
 - Intrinsic: The instruction defaults to a set integer value unless followed by a integer value, then that is taken as input instead
 - Caret: The instruction take the value at the caret as input
 - Source: The instruction take the following integer or character from the source code as input
 - Input: Takes input from standard input

There also exists a special character the dot: `.` which modifies the preceding instruction in some way. Most often it swaps the input mode of the instruction from Caret -> Source or from Source/intrinsic -> Caret
## List of instructions:

#### Move instructions
 - `><_^` //right, left, down, up respectively
 - Intrinsic: read head size in relevant direction

Note: the instruction `>1` moves the caret one bit to the right whilst `>` moves the caret by "read-head-width"-bits completely clearing the read head of the bits of the last location of the readhead

