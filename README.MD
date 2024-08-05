# Multithreading 

## Problem Statement - 1
1. Write program to count prime numbers till 100million
  1. Sequential Approach : Took time 30160 ms
  2. Parallel Approach : Took time 7477 ms   
     
## Problem Statement - 2

1. Write program to count numbers from 0 to 1 million
   1. Without lock : count is not equal to 1 million
   2. With mutex : count is equal to 1 million
2. Optimistic lock poc using compare and swap 

## Problem Statement - 3

1. Write a program which create deadlock

### Approach

Create 3 records and 6 transactions, Each transaction will try to acquire lock in any of 2 records in random fashion
which will create deadlock
For prevention deadlock, try to acquire lock in records in order fashion only
