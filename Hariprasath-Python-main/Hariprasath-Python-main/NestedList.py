


student = []
for i in range(int(input())):
        name = input()
        score = float(input())
        student.append([name, score])
second_highest = sorted(set([score for name, score in student]))[1]
print('\n'.join(sorted([name for name, score in student if score == second_highest])))
    