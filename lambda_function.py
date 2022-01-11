import json
import boto3
import re

def eqChecklowerBounded(numberArr, intervalUpper,i):
    if i==len(numberArr):
        return 0
    else:
        if numberArr[i]<=intervalUpper[i]:
            return eqChecklowerBounded(numberArr, intervalUpper, i+1)
        return -1
        
def eqCheckupperBounded(numberArr, intervalLower,i):
    if i==len(numberArr):
        return 0
    else:
        if numberArr[i]>=intervalLower[i]:
            return eqCheckupperBounded(numberArr, intervalLower, i+1)
        return 1

def eqCheck(numberArr, intervalLower, intervalUpper, i):
    if i==len(numberArr):
        return 0
    else:
        if numberArr[i]>intervalLower[i]:
            return eqChecklowerBounded(numberArr, intervalUpper, i)
        elif numberArr[i]<intervalUpper[i]:
            return eqCheckupperBounded(numberArr, intervalLower, i)
        elif (numberArr[i]>=intervalLower[i]) and (numberArr[i]<=intervalUpper[i]):
            return eqCheck(numberArr, intervalLower, intervalUpper, i+1)
        elif numberArr[i]>intervalUpper[i]:
            return 1
        else:
            return -1


def binary_search(arr, low, high, intervalLower, intervalUpper):
 
    # Check base case
    if high >= low:
 
        mid = (high + low) // 2
        
        time = re.split("[.]|:| ", arr[mid])
        numberArr=[]
        numberArr.append(int(time[0]))
        numberArr.append(int(time[1]))
        numberArr.append(int(time[2]))
        numberArr.append(int(time[3]))
        print("Arr")
        for i in numberArr:
            print(i)
        
        # If element is present at the middle itself
        check = eqCheck(numberArr, intervalLower, intervalUpper, 0)
        if check==0:
            return mid
 
        # If element is smaller than mid, then it can only
        # be present in left subarray
        elif check==-1:
            return binary_search(arr, low, mid - 1, intervalLower, intervalUpper)
 
        # Else the element can only be present in right subarray
        else:
            return binary_search(arr, mid + 1, high, intervalLower, intervalUpper)
 
    else:
        # Element is not present in the array
        return -1

def lambda_handler(event, context):
    # TODO implement
    s3 = boto3.client('s3',aws_access_key_id='---',aws_secret_access_key='-----')
    print('Object in bucket')
    original = s3.get_object(
        Bucket='arn:aws:s3:us-east-2:290271741690:accesspoint/myap',
        Key='input.log')
    # print(original['Body'].read().decode('utf-8'))
    fullString = original['Body'].read().decode('utf-8')
    mylist = fullString.splitlines()
    inputTime = re.split("[.]|:", event['time'])
    inputDelta = re.split("[.]|:", event['dt'])
    
    if len(inputTime)!=4:
        return {
            'statusCode': 500
        }
    
    if len(inputDelta)!=4:
        return {
            'statusCode': 500
        }
    
    intervalUpper = []
    intervalLower = []
    carryOver=0
    for f, b in zip(reversed(inputTime), reversed(inputDelta)):
        number1=int(f)
        print(f)
        number2=int(b)
        print(b)
        if len(intervalUpper)==3:
            intervalUpper.append(number1+number2+carryOver)
        elif len(intervalUpper)==0:
            if (number1+number2)>=999:
                intervalUpper.append(number1+number2-999)
                carryOver=1
            else:
                intervalUpper.append(number1+number2)
                carryOver=0
        else:
            if (number1+number2+carryOver)>=60:
                intervalUpper.append(number1+number2-60+carryOver)
                carryOver=1
            else:
                intervalUpper.append(number1+number2+carryOver)
                carryOver=0
            
    for f, b in zip(reversed(inputTime), reversed(inputDelta)):
        number1=int(f)
        print(f)
        number2=int(b)
        print(b)
        if len(intervalLower)==3:
            intervalLower.append(number1-number2-carryOver)
        elif len(intervalLower)==0:
            if (number1-number2)<0:
                intervalLower.append(number1-number2+999)
                carryOver=1
            else:
                intervalLower.append(number1-number2)
                carryOver=0
        else:
            if (number1-number2-carryOver)<0:
                intervalLower.append(number1-number2+60-carryOver)
                carryOver=1
            else:
                intervalLower.append(number1-number2-carryOver)
                carryOver=0
                
    intervalLower.reverse()
    intervalUpper.reverse()
    for i in intervalLower:
        print(i)
    for i in intervalUpper:
        print(i)
    retVal = binary_search(mylist, 0, len(mylist)-1, intervalLower, intervalUpper)
    print(retVal)
    return {
        'statusCode': 200,
        'body': json.dumps(str(retVal))
    }
