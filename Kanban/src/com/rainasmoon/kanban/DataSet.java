package com.rainasmoon.kanban;

import java.util.HashSet;
import java.util.Set;

public class DataSet {

	private Set<String> backlog;
	private Set<String> plan;
	private Set<String> inProcess;
	private Set<String> completed;

	public DataSet() {
		backlog = new HashSet<String>();
		plan = new HashSet<String>();
		inProcess = new HashSet<String>();
		completed = new HashSet<String>();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(backlog);
		sb.append(plan);
		sb.append(inProcess);
		sb.append(completed);
		return sb.toString();
	}

	public Set<String> getBacklog() {
		return backlog;
	}

	public void setBacklog(Set<String> backlog) {
		this.backlog = backlog;
	}

	public Set<String> getPlan() {
		return plan;
	}

	public void setPlan(Set<String> plan) {
		this.plan = plan;
	}

	public Set<String> getInProcess() {
		return inProcess;
	}

	public void setInProcess(Set<String> inProcess) {
		this.inProcess = inProcess;
	}

	public Set<String> getCompleted() {
		return completed;
	}

	public void setCompleted(Set<String> completed) {
		this.completed = completed;
	}

	public void removeBacklog(String item) {
		backlog.remove(item);

	}

	public void removePlan(String item) {
		plan.remove(item);

	}

	public void removeInProcess(String item) {
		inProcess.remove(item);

	}

	public void removeCompleted(String item) {
		completed.remove(item);

	}

	public void addBacklog(String item) {
		if (backlog == null) {
			backlog = new HashSet<String>();
		}
		backlog.add(item);

	}

	public void addPlan(String item) {
		if (plan == null) {
			plan = new HashSet<String>();
		}
		plan.add(item);

	}

	public void addInProcess(String item) {
		if (inProcess == null) {
			inProcess = new HashSet<String>();
		}
		inProcess.add(item);

	}

	public void addCompleted(String item) {
		if (completed == null) {
			completed = new HashSet<String>();
		}
		completed.add(item);

	}
}
