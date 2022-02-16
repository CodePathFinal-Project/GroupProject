// Login (READ/GET)
private fun loginUser(username: String, password: String) {
   ParseUser.logInInBackground(username, password, ({ user, e ->
       if (user != null) {
           Log.i(TAG, "Successfully logged in user")
           goToMainActivity()
       } else {
           e.printStackTrace()
           Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT).show()
       }})
   )
}

// User Request (READ/GET)
var currentUser: User = null
query.findInBackground(object: FindCallback<Post> {
   override fun done(user: User, e: ParseException?) {
       if (e != null) {
           //Something went wrong
           Log.e(TAG, "Error fetching user information")
       } else {
           if (user != null) {
               for (user in posts) {
                   Log.i(TAG, "User: " + user.Info())
               }
               currentUser = user
               adapter.notifyDataSetChanged()
           }
       }
   }

})

// Cycle Request (READ/GET)
var allCycles: MutableList<Post> = mutableListOf()
query.findInBackground(object: FindCallback<Post> {
   override fun done(cycles: MutableList<Cycle>?, e: ParseException?) {
       if (e != null) {
           //Something went wrong
           Log.e(TAG, "Error fetching cycles")
       } else {
           if (cycles != null) {
               for (cyle in posts) {
                   Log.i(TAG, "Cycle: " + cycle.getDescription())
               }
               allCycles.addAll(cycles)
               adapter.notifyDataSetChanged()
           }
       }
   }

})

// DailyInput (READ/GET)
var allDailyInputs: MutableList<DailyInput> = mutableListOf()
query.findInBackground(object: FindCallback<DailyInput> {
   override fun done(dailyInputs: MutableList<DailyInput>?, e: ParseException?) {
       if (e != null) {
           //Something went wrong
           Log.e(TAG, "Error fetching daily inputs")
       } else {
           if (posts != null) {
               for (dailyInput in dailyInputs) {
                   Log.i(TAG, "Post: " + post.getDescription())
               }
               allDailyInputs.addAll(dailyInputs)
               adapter.notifyDataSetChanged()
           }
       }
   }

})
