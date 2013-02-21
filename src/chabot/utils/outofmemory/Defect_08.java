package chabot.utils.outofmemory;

/* DEV-39170
 * Resolution
 * 1. Open the dominator tree
2. Sort by percent descending
3. The largest class will either be:
a. a HH thread with name similar to java.lang.Thread @<address> Sync08:<user_nm>:2:<sessionlog_id>:Submission[PATIENTID-<pat_id>]
b. a web thread (or ThreadWithAttributes) similar to: org.apache.tomcat.util.threads.ThreadWithAttributes @<address> http-0.0.0.0-31140-Processor6
4. Expand this thread
5. To match this dev issue the 1st or 2nd next largest class under the Thread class will be com.patientkeeper.datamodel.ProblemList (size of the retained or shallow heap is irrelevant in this issue)

Please note that simply searching for .Problem. in the histogram will not truly identify this dev issue: DEV-39170
 * 
 * Signature
 * The largest class in dominator tree will be either:
a. a HH thread with name similar to java.lang.Thread @<address> Sync08:<user_nm>:2:<sessionlog_id>:Submission[PATIENTID-<pat_id>]
or
b. a web thread (or ThreadWithAttributes) similar to: org.apache.tomcat.util.threads.ThreadWithAttributes @<address> http-0.0.0.0-31140-Processor6

Expand the above thread and the 1st or 2nd next largest class under the Thread class will be com.patientkeeper.datamodel.ProblemList

Fix version 7.6.6
 */

public class Defect_08 {

}
