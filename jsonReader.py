import json
import os
import csv

main_dir = os.getcwd()+'/GradiateProcessing2'
new_dir = os.getcwd()+'/GradiateProcessingCSVS'
if('GradiateProcessingCSVS' not in os.listdir(os.getcwd())):
    os.mkdir(new_dir)
print(main_dir)
files = []
for item in os.listdir(main_dir):
    if not item.startswith('.'):
        files.append(item)
newFiles = []
for fi in files: 
    with open(main_dir + '/' + fi + '/Gradiate(0)_csf.json') as f:
        data = json.load(f)

    idNum = data["name"][0:2]
    trial = data["name"][2]

    myList = []

    sweeps = data["sweeps"]
    group = 0
    for i in range(len(sweeps)):
        tempList = []
        longest = 0
        longestGroup = 0
        for j in range(len(sweeps[i]["trials"])):
            group+=1
            if(sweeps[i]["trials"][j]["duration"] > sweeps[i]["trials"][longest]["duration"]):
                longest = j
                longestGroup = group
        key = data["sweeps"][i]["key"]

        tempList.append(longestGroup-1)
        tempList.append(idNum)
        tempList.append(trial)
        tempList.append(sweeps[i]["trials"][longest-1]["sf"])
        tempList.append(sweeps[i]["trials"][longest-1]["contrast_ratio"])
        tempList.append(sweeps[i]["trials"][longest-1]["duration"])
        tempList.append(key)
        myList.append(tempList)
        tempList = []

        tempList.append(longestGroup)
        tempList.append(idNum)
        tempList.append(trial)
        tempList.append(sweeps[i]["trials"][longest]["sf"])
        tempList.append(sweeps[i]["trials"][longest]["contrast_ratio"])
        tempList.append(sweeps[i]["trials"][longest]["duration"])
        tempList.append(key)
        myList.append(tempList)

    fields = ["Group", "ID", "Trial", "SpatialFrequency", "ContrastRatio", "Duration", "Key"]

    if idNum not in newFiles:
        with open(new_dir+data["name"][0:2]+'.csv', 'w') as f:
            write = csv.writer(f)
            write.writerow(fields)
            write.writerows(myList)
        newFiles.append(idNum)
    else:
        with open(new_dir+data["name"][0:2]+'.csv', 'w') as f:
            write = csv.writer(f)
            write.writerows(myList)
        newFiles.append(idNum)