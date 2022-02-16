Original App Design Project
===

# Period Tracker

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
App_name is a period tracker.

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Health/Lifestyle 
- **Mobile:** Android
- **Story:** Analyses user's menstrual cycle and keeping track user's symptoms at the same time predict the future cycle
- **Market:** Any woman who wants to keep track of their menstration cycle
- **Habit:** Uses the app daily to keep track of moods or symptoms.
- **Scope:** Taking user inputs and store on their local device, having simple calculation device and calendar interface.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* [fill in your required user stories here]
* prompt the user to give input (first day of the cycle, end day of the cycle, moods, symptoms, menstrural flow and craving)
* highlighted from the first day to end of the cycle
* show the expected date of the next cycle
* Settings e.g Notifications (end date and predicted start date)
* 

**Optional Nice-to-have Stories**

* [fill in your required user stories here]
* Change the color palete 
* Logo Design

### 2. Screen Archetypes
* Login 
* Register - User signs up or logs into their account
   * Upon Download/Reopening of the application, the user is prompted to log in to gain access to their profile information. 

* Setting screen
   * Lets people to change notification/reminders, theme, password management
  
* Calendar view
   * Allows the user to input the start and end dates of the menstrual cycle
   * Allows the user to see predictions for their cycle

* Daily input view
  * A pop up from calendar view (after a user clicks on a specific date, this view shows up)
  * Lets the user track their start&end date of the cycle, mood, symptoms, etc.



### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Calendar 
* Settings


**Flow Navigation** (Screen to Screen)

* Forced Log-in -> Account creation if no log in is available
  * Register 
   
* Calendar 
   * Daily Input

* Settings

## Wireframes

<img src="https://user-images.githubusercontent.com/71671419/153700766-39254979-c737-40a2-be63-25cb7400ff67.png" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]

### Model
**Cycle class**
| Property   | Type | Description |
| ------------- | ------------- | ------------- |
| `userId`  | Pointer to User  | Unique id for different user account |
| `cycleId`  | Int  | Unique id for user's cycle |
| `startedAt` | DateTime | Date for when the user input their 1st day of the cycle |
| `endedAt` | DateTime | Date for when the user input their cycle ended |

**DailyInput Class**
| Property  | Type | Description |
| ------------- | ------------- | ------------- |
| `userId`  | Pointer to User  | Unique id for different user account |
| `cycleId` | Int | Unique id for each user's cycle|
| `Cramp` | Int | On a scale 0-10 how the user experienced cramp |
| `Fatigue` | Int | On a scale 0-10 how the user experienced fatigue |
| `Energy` | Int | User energy level on the scale of 0 - 10 |
| `Acne` | Int | User acne breakout on the scale of 0 - 10 |

**User Class**
| Property  | Type | Description |
| ------------- | ------------- | ------------- |
| `userId`  | String  | Unique id for different user account |
| `password` | String | User authentication alog with username |
| `username` | String | User authentication alog with password |
| `cycleLength` | Int | User cycle length |
| `periodLength` | Int | User period length | 

[Add table of models]
### Networking
- get started screen
   - (Create/POST) post user’s answers to introductory questions to User class
   - (Create/POST) post user’s desired username and password
- sign in screen
   - (Read/GET) verify the password and user if the username and password match, the user is successfully logged in
- calendar view
   - (Read/GET) query logged in user’s startedAt and endedAt dates
- date view
   - (Create/POST) post user’s inputted start and end date and symptoms
   - (Update/PUT) update user’s inputted start and end date and symptoms
- settings screen
   - (Read/GET) query user’s period length, cycle length, username and password
   - (Update/PUT) update user’s period length, cycle length, username and password
- [Create basic snippets for each Parse network request]

