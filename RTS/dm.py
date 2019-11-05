# DM- Deadline Monotonic

"""
3
50 25 50 100
0 10 62.5 20
0 25 125 50
"""
import math
def gcd(a,b) :
    if (a < b) :
        return gcd(b, a)

    # base case
    if (abs(b) < 0.001) :
        return a
    else :
        return (gcd(b, a - math.floor(a / b) * b))
def lcm(a,b):
    return (a*b)/gcd(a,b)

num=int(input())
tasks=[list(map(float,input().split()))+[_+1] for _ in range(num)]

# priority according to DM
order={ val[4]:idx for idx,val in enumerate(sorted(tasks, key = lambda x: x[3])) }


H=1
for val in tasks:
    H=lcm(H, val[2])
H=round(H)
#print(H)

schedule=[]
todo=[[] for i in range(H)]
for task in tasks:
    #print(task)
    arrival_time=int(task[0])
    deadline=task[3]
    period=int(task[2])
    execution_time=task[1]
    num=task[4]
    ch=0
    for arr in range(arrival_time, H, period):
        ch+=1
        todo[arr].append([arr, execution_time, deadline + (ch-1)*period, str(num)+str(ch), num])


schedule_with_preemption=['' for i in range(H)]

previous=[]
pos=0
while pos<H:
    if todo[pos] or previous:
        previous.extend(todo[pos])
        mn=min(previous, key= lambda x: order[x[4]])
        nx=[]
        for job in previous:
            if job!=mn:
                nx.append(job)
        previous=nx[:]
        schedule_with_preemption[pos]=mn[3]
        pos+=1
        mn[1]-=1
        while pos< H and not todo[pos] and mn[1]:
            schedule_with_preemption[pos]=mn[3]
            pos+=1
            mn[1]-=1
        if mn[1]:
            previous.append(mn)

    else:
        pos+=1


# print result
print("Time", "Job", sep='\t')
for i in range(H):
    print(i+1,schedule_with_preemption[i], sep='\t')
