Example request
```json
{
  "vetEmail": "vet@example.com",
  "timeOffset": {
    "value": 2,
    "unit": "months"
  },
  "weekDay": "Monday",
  "pet": {
    "name": "Buddy",
    "birthday": "2018-04-23",
    "ownerEmail": "owner@example.com"
  },
  "reminderType": "check-up",
  "message": "Reminder for Buddy's regular check-up."
}
```
Request
```json
{
  "vetEmail": "vet@example.com",
  "timeToAdd": {
    "amount": 2,
    "unit": "months"
  },
  "weekDay": "Tuesday",
  "petName": "Buddy",
  "petBirthday": "2020-05-15",
  "ownerEmail": "owner@example.com",
  "reminderType": "check-up",
  "message": "Buddy's annual check-up."
}
```

Response 
```json
{
  "status": "success",
  "reminderId": "abc123",
  "reminderDate": "2024-12-17",
  "vetEmail": "vet@example.com",
  "petName": "Buddy",
  "petBirthday": "2020-05-15",
  "ownerEmail": "owner@example.com",
  "reminderType": "check-up",
  "message": "Buddy's annual check-up."
}
```

Request
```json
{
  "vetEmail": "vet@example.com",
  "timeOffset": {
    "amount": 2,
    "unit": "months"
  },
  "weekDay": "Tuesday",
  "petName": "Buddy",
  "petBirthday": "2020-05-15",
  "ownerEmail": "owner@example.com",
  "reminderType": "check-up",
  "message": "Buddy's annual check-up."
}
```

Response
```json
{
  "status": "success",
  "reminderId": "abc123",
  "reminderDate": "2024-12-17",
  "vetEmail": "vet@example.com",
  "petName": "Buddy",
  "petBirthday": "2020-05-15",
  "ownerEmail": "owner@example.com",
  "reminderType": "check-up",
  "message": "Buddy's annual check-up."
}
```

Request
```json
{
  "vetEmail": "vet@example.com",
  "timeOffset": {
    "amount": 2,
    "unit": "months"
  },
  "weekDay": "Tuesday",
  "petName": "Buddy",
  "petBirthday": "2020-05-15",
  "ownerEmail": "owner@example.com",
  "reminderType": "check-up",
  "message": "Buddy's annual check-up."
}
```

Response
```json
{
  "reminderDate": "2024-12-17",
  "vetEmail": "vet@example.com",
  "petName": "Buddy",
  "petBirthday": "2020-05-15",
  "ownerEmail": "owner@example.com",
  "reminderType": "check-up",
  "message": "Buddy's annual check-up."
}
```

