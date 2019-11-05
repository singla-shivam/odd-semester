"""
3
7 3
12 3
20 5
"""
def check(wn, idx, p, c, priority):
    wn1=c[priority[idx]]+sum([((wn+p[i]-1)//p[i])*c[i] for i in priority[:idx] ])
    return wn1

num=int(input())
p,c=[],[]
priority=list(range(num))
for i in range(num):
    a,b=map(int,input().split())
    p.append(a)
    c.append(b)

priority.sort(key=lambda x: c[x])

ans=[False for i in range(num)]
for idx in range(num):
    wn=c[priority[idx]]
    while 1:
        wn1=check(wn, idx, p, c, priority)
        print(priority[idx],wn,wn1)
        if wn1<=p[idx]:
            if wn==wn1:
                ans[idx]=True
                break
            else:
                wn=wn1
        else:
            break
print("JOBS\t",*priority,sep="\t")
print("schedulable",*ans,sep="\t")
