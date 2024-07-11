
"""
Given  sets of integers,M  and N, print their symmetric difference in ascending order. The term symmetric difference indicates those values that exist in either M or N  but do not exist in both.
"""








n=int(input())
set1=set(map(int,input().split()))
m=int(input())
set2=set(map(int,input().split()))
set3=set1.symmetric_difference(set2)
set3=list(set3)
set3.sort()
for i in set3:
    print(i)