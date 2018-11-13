# CodeAssignment
Test:
Compile all the file and run the main function.
(javac *.java
java Main    )
I have written a few test cases in the main function. You can directly compile all the class and run the main function to see it.

Design:
Rule class: store ip and port range.
Firewall class: Use packet direction and protocol as keys to store rules sets.
accept_packet: use direction and protocol as keys to check whether the packet is allowed.
MergeRules: Merge rules that have either overlapping port or overlapping ip which can save spaces. There is a tradeoff between time since it requires more time to build the Firewall. However, if you have thousands of rules, merge rules can save a lot of space for you.

Since I use hashmap to store all the rules and I use merge function. The Time to build Firewall should be O(n^2) and the time to check accept_packet will be O(1).

Future optimization:
If I have more time, I will write more test cases to verified both my merge function and some edge cases. And I do not implement any function to support adding or removing rules after the Firewall is built.

Area:
1 Platform team
2 Policy team