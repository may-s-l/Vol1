package Domain;

public class Job {
    private String jobName;

    public Job(String jobName) {
        this.jobName = jobName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    @Override
    public String toString() {
        return "Job: "+ this.jobName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || ! (o instanceof Job)) {
            return false;
        }
        Job other = (Job) o;
        return this.getJobName()==other.getJobName();
    }
}
