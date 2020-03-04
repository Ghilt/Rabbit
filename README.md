
 <img align="right" src="https://user-images.githubusercontent.com/3304335/30387851-2aee32a2-98ae-11e7-89f1-18619c839791.png" width="150">
 
 # Rabbit~ 

A recreational esoteric language which tries to do things. A little golf, a little 2d grid storage, a little consistency here and there and some other things. Inspired by many of the esoteric programming languages out there.

## Memory model

Rabbit~ uses a grid of bits to store its data and the program itself. Each program has exactly one 'bitgrid'. The bits are modified and interpreted with help of concepts called 'caret' and 'read head'. 

The carets are analogous to carets in a ordinary word processor, they mark where on the grid data will be read or stored. 

Starting from the caret the read-head decides which bits will be read and interpreted as an integer or character (floats: coming soon, absolutely, they seem useful to have). The readhead marks a rectangular region which is read left to right, top to bottom.

![caretreadhead](https://user-images.githubusercontent.com/3304335/30440141-f68fa5b8-9975-11e7-965b-f8d384bd99eb.png)

In the image above you can see the default readhead dimensioned at 8x4 giving you 32 bits by default, the caret is also marked. If the program would read and integer in this position shown it would give 7168. Note: Numbers are signed and represented in two's complement.

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

## Syntax

Instructions in Rabbit~ are one character, almost all characters are considered instructions. It is possible to write erroneous syntax but it is not as easy as in other languages. This is of course not really a good thing, which is somewhat fitting for this language.

There are four default input modes for instructions:
 - Intrinsic: The instruction defaults to a set integer value unless followed by a integer value, then that is taken as input instead
 - Caret: The instruction take the value at the caret as input
 - Source: The instruction take the following integer or character from the source code as input
 - I/O: Special for I/O instructions

There also exists a special character, the dot: `.` which modifies the preceding instruction in some way. Most often it swaps the input mode of the instruction from Caret -> Source or from Source/Intrinsic -> Caret

### Examples

#### HelloWorld:

`HelloWorldH[:.>]`

#### Print Fibbonacci numbers

`1$>;1[\:+\+\+]`

Step by step explanation:
Default readhead: 8 wide, 4 high
Default caret: x = 0, y = 0

 - `1`: store 1 at C1(0,0)
 - `$`: create C2(0,0) at C1(0,0)
 - `>`: move C1(0,0) -> C1(8,0)
 - `;1`: store 1 at C1(8,0) // We need to use explicit store instruction here to separate the number from the move command
 - `[`: start loop, record value at C1(8,0), which is 1
 - `\`: swap active caret to C2(0,0)
 - `:`: print value at C2(0,0), which is 1, to standard output 
 - `+`: record value at C2(0,0), which is 1, to be added
 - `\`: swap active caret to C1(8,0)
 - `+`: record value at C1(8,0), which is 1, to be added
 - `\`: swap active caret to C2(0,0)
 - `+`: perform addition of prepared values and store at C2(0,0), 1 + 1 = 2
 - `]`: compare value of active caret C2(0,0) with recorded value of loop 1 != 2 so continue
 - Then the next loop cycle start at `\` as you'd expect. Since there are 3 caret swaps in the loop and only 2 carets it all works out brilliantly. The loop never ends.
 
 #### Small calculator program:

Here is a small program which you can use as a simple calculator. The user enters commands analogous to the arithmetic operations in Rabbit~, which is performed and then exits when you enter an '='. Enter is required after each input in the formula, a valid input sequence for the below programs would be `81 * 4 + 2 / 2 =` which would output `163` and then terminate the program.

Here it is implemented with a loop and two memory carets

    $>=@[.0={.=}{@:¤}!\@!\!\=!\!!@].1

And here it is using Rabbit~'s function syntax and a single caret. These two programs behaves exactly the same.

    _=>={.=}{<:¤}>=<!<!>!>!<!<!^>;"<#.>##

As you can see they are both short and quite unreadable, but a sense of structure can be perceived if you compare them. The flow of exclamation points and move commands in the second one is objectively very satisfying with no double moves. Consult the command list at below to find enlightenment of how these two programs work. 

## List of instructions

Here follows a complete listing of all instructions grouped by similarity.

Some instructions will be described as 2-cyclic or 3-cyclic. That means they do different things depending if it's the first, second (or third) instruction in the 'cycle'. Most often these instructions are for operations that require more than one input and output.  

#### Move instructions
    > right
    < Left
    ^ Up
    _ Down
    
    Default input: Intrinsic - read head size in relevant direction

<i>Note:</i> the instruction `>1` moves the caret one bit to the right whilst `>` moves the caret by "read-head-width"-bits completely clearing the read head of the bits of the last location of the readhead,

#### Arithmetic and bit-wise instructions

These instruction are 3-cyclic and all work the same way. The first two instructions read a value from input while the final instruction in the cycle outputs the result to the bitgrid. 

    + plus
    - minus
    * multiply
    / divide
    % modulo
    & and
    | or
    ¨ xor
    ~ flip (invert)
    « shift left
    » shift right
    
    Default input: Caret

If the modifier `.` is applied in stage 3 on any of these instructions, it will skip outputting for another cycle and take input.

#### Loop instructions

    ()
    []
    Default input: Caret

In the snippets below the first line is symbolising code. The capital letters in the first line symbolize any non `[]()` instruction. The second line is the bracket's input which by default is the value at the caret.

The ordinary loop instructions checks the value under the caret and compares it to the value under the other loop instruction.  

     [ABCD]
     v1   v2
    
If v1 == v2 then exit loop
     
     ]ABCD[
     v1   v2

If v1 != v2 then exit loop
    
  
Worth noting is that v1 is recorded as it is reached the frist time. The loop compares a value which might no longer be present in the bitgrid to a value in the bitgrid.

    (ABCD)  
    v1

Loop v1 times

    )ABCD( 
         v1
 
 Loop v1 times     
    
Using the inverted case here guarantees at least one iteration which can be useful. Loops can not overlap, the following is not two loops but the begining of four loops. Which, if the program terminates before a matching end loop is executed, will have done nothing

    ( [ ) ] 
    

If you think those inverted bracket instructions are kind of ugly wait until you see the conditional construction, speaking of which:

#### Conditional instruction

    {}
    Default input: Caret
    
In the snippets below the first line is symbolising code, where capital lettes symbolize any non `{}` instruction. The second line is symbolizing the bracket's input, first input and second input.

    {ABCDE}FGHI{JKL}MNO
    v1    v2   

Lets look at the simplest case, the above means if v1 == v2 then when the last { is reached perform the instructions until } is reached then the conditional statment is over. If v1 != v2 then JKL is skipped.

Simple. This is how an if X do Y else Z clause would look like:

    {ABC}FGH}IJK{LMN{OPQ}RST
    v1    v2 
   
if v1 == v2 do IJK else do OPQ. Clear as day!

if-else-chain with an arbitary amount of branches works like this (the below is if-elseif-else):

    {ABCD}EFGH}IJK{LMN}OPQ{RST}U{VXY{ZAB}
    v1   v2          v3  v4
   
if v1 == v2 do IJK else if v3 == v4 do U else do ZAB. You can canstruct as many branches as you wish in this way. Below is the  same example again but with values and instructions not executed represented by underscores:

    {ABCD}EFGH}___{LMN}OPQ{RST}_{VXY{ZAB}
    7    13           8   8
    
That is almost all there is to conditions. The observant may have noticed that the conditional always start with a normal {}-bracket-pair and ends with a normal {}-bracket-pair with a number of inverted }{-bracket-pairs in between. That does not have to be the case; if you were to invert all the brackets in the snippets above you would still end up with valid Rabbit~ code. All it would do would be to invert the conditions (va != v2, v3 != v4). I'll leave it as an excersize for the interested reader to determine which code will be run in the last example if all the brackets were inverted.  

#### Incease/Decrease

    ' increase
    , decrease
    Default input: Instrinsic 1

Increases or decreases value under caret. 

#### Copy

    @ copy
    Default input: Caret

This is a 2-cyclic instruction. Copy the value at the caret at the first instruction and store it at the caret at the second instruction 

#### Store

    ; store
    Default input: Source
    
Store input to instruction at caret. Useful to transfer some data to the bitgrid from the source code
  
#### Configure read head
  
    ´ configure readhead
    Default input: Intrinsic: Doubles the size

This is a 2-cyclic instruction. First you configure width, then height, only when you have configured both (a.k.a on the second instruction) does the change take effect. (This is subject to be updated in future versions, a split for execution readhead and memory readhead is likely)

#### Execute

    ! execute simple
    £ execute
    Default input: Caret
    
`!` is a 2 cyclic instruction. It records what instruction to perform, and then executes it at the second instruction. The instruction is executed as if it were an ordinary instruction being executed.

`£` is a 3 cyclic instruction. It records what instruction to perform, then records what input to give to this instruction and then executes it at the third instruction. The instruction is executed as if it were a ordinary instruction being executed.



#### Terminate executiontrack

    ¤ terminate
    Alias: ...00000000
    Default input: Caret
    
Terminates execution track. A completely empty blank instruction also does this.

#### New caret

    $ spawn new caret
    Default input: Instrinsic 1
    
Spawns a new memory caret at current caret position. Input decides how many to create. You do not swap to the caret you created. The carets are numbered and the standard caret is the 0'th caret and the next one you creates is the 1'st and then the 2'nd one and so forth.

#### Change active caret

    \ Change caret
    Default input: Instrinsic cycle to next caret
    
By default cycles through the created carets. If `.` is used it chooses caret based on the order they were created and reads from the bitgrid. While `\1` reads from source and selects the caret you yourself created first in your program.

#### Start function
    
    # start function
    Default input: Caret   
   
This is a 3 cyclic instruction. It sets in motion a new execution track. The first instruction records what direction the new execution track will progress in. The second instruction records where it will begin executing. The third instruction records where it's default memory caret will be and then adds it to the pool of execution tracks in the program.

#### Query environment
    
    ? query environment
    Default input: Source
    
Query environment for the following information and store it in bit grid

    v = Rabbit~ version
    w = read head width
    h = read head height
    s = read head size
    c = active caret number
    a = amount of active carets
    x = active caret x coordinate
    y = active caret y coordinate
    M = max value of integers depending on your current read head
    m = min value of integers depending on your current read head
    o = fills readhead with 1's
    u = most significant half of read head filled with 1's rest 0's
    l = least significant half of read head filled with 1's rest 0's
    arithmetic or bitwise instruction(+,-,* etc) = either zero or the first value of the ongoing instruction cycle
 
#### I/O

    = read
    : print
    Default input: I/O
    
Read reads from standard input and stores in bit grid, and print prints to standard out.

The `.` is special for these two instructions:
  - Read can only store one character at a time to the bitgrid. The rest is stored for future use so `=.` uses this as input instead and do not require the user to enter anything.
  - Normal print prints the byte represented as an integer,  `:.` prints a character
 
#### Character instructions

    remaining characters or bit patterns
    Default input: Source
    
A character instruction simply reads itself back into the memory and does nothing else. A character isntruction modified with `.` is as of Rabbit~ v.1 undefined
    
### Non instruction character

Besides `.` two other special characters is reserved:

    ƒ
    Ð
    
`ƒ` is a meta character which has a special meaning when read from the source file. It is not written to the bit grid as the `.` is. The `ƒ` separates the source into execution tracks. The execution tracks are stored in the bitgrid above and below each other one read head apart with the first one being the top one.

You cannot specify direction as you can when adding execution track from the code.


`Ð` is similar to `ƒ` but instead of starting an execution track it just transfers its data chunk into the grid on it's corresponding row. It also employs a special encoding scheme to easier be able to transfer bytes which normally isn't printable.

When using `Ð` the follwoing translation is made:

    Ạ Ḅ Ḍ Ẹ Ḥ Ị Ḳ Ḷ Ṃ Ṇ Ọ Ṛ Ṣ Ṭ Ụ Ṿ Ẉ Ỵ Ẓ Ȧ Ḃ Ċ Ḋ Ė Ḟ Ġ Ḣ İ Ŀ Ṁ Ṅ Ȯ Ṗ Ṙ Ṡ Ṫ Ẇ Ẋ Ẏ Ż
    0 1 2 3 4 ...                                                                 39

     ạ ḅ ḍ ẹ ḥ ị ḳ ḷ ṃ ṇ ọ ṛ ṣ ṭ ụ ṿ ẉ ỵ ẓ ȧ ḃ ċ ḋ ė ḟ ġ ḣ ŀ ṁ ṅ ȯ ṗ ṙ ṡ ṫ ẇ ẋ ẏ ż
     |   |   |                                                                   |
    128 130 142 ...                                                             166

    ɲ = 208
    ɱ = 402

## Interpreter flags & input

The interpreter is used:

path-to-source-file [flag input | input]

If an argument is not preceded by a flag then it is taken as '-m' input, see below:

- `-r width,height` Configure default read head size (comma separated)
- `-m input` if input is parsable as an integer then it is read into the bitgrid as an integer otherwise as a sequence of characters
- `-c input` load input as a sequence of characters
- `-f filePath` load input from a file

The input is loaded into the bitgrid below any data from the source code.

  
