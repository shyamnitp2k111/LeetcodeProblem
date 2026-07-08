package company.eBay;

import java.util.*;

public class eBay {

    enum Type {
        EPIC,
        PROJECT,
        STORY,
        TASK,
        BUG
    }

    static class WorkItem {

        String id;
        Type type;

        List<WorkItem> children = new ArrayList<>();

        public WorkItem(String id, Type type) {
            this.id = id;
            this.type = type;
        }

        public void addChild(WorkItem child) {
            children.add(child);
        }
    }

    // ==========================================
    // 1. Print Entire Hierarchy
    // ==========================================

    public static void printHierarchy(WorkItem node) {

        System.out.println(node.type + " -> " + node.id);

        for (WorkItem child : node.children) {
            printHierarchy(child);
        }
    }

    // ==========================================
    // 2. Find Complete Impact
    // ==========================================

    public static void findImpact(WorkItem node) {

        System.out.println(node.type + " -> " + node.id);

        for (WorkItem child : node.children) {
            findImpact(child);
        }
    }

    // ==========================================
    // 3. Get All Stories
    // ==========================================

    public static void getStories(WorkItem node,
                                  List<WorkItem> stories) {

        if (node.type == Type.STORY) {
            stories.add(node);
        }

        for (WorkItem child : node.children) {
            getStories(child, stories);
        }
    }

    // ==========================================
    // 4. Get All Tasks
    // ==========================================

    public static void getTasks(WorkItem node,
                                List<WorkItem> tasks) {

        if (node.type == Type.TASK) {
            tasks.add(node);
        }

        for (WorkItem child : node.children) {
            getTasks(child, tasks);
        }
    }

    // ==========================================
    // 5. Get All Bugs
    // ==========================================

    public static void getBugs(WorkItem node,
                               List<WorkItem> bugs) {

        if (node.type == Type.BUG) {
            bugs.add(node);
        }

        for (WorkItem child : node.children) {
            getBugs(child, bugs);
        }
    }

    // ==========================================
    // 6. Count Stories
    // ==========================================

    public static int countStories(WorkItem node) {

        int count = 0;

        if (node.type == Type.STORY) {
            count++;
        }

        for (WorkItem child : node.children) {
            count += countStories(child);
        }

        return count;
    }

    // ==========================================
    // 7. Count Tasks
    // ==========================================

    public static int countTasks(WorkItem node) {

        int count = 0;

        if (node.type == Type.TASK) {
            count++;
        }

        for (WorkItem child : node.children) {
            count += countTasks(child);
        }

        return count;
    }

    // ==========================================
    // 8. Count Bugs
    // ==========================================

    public static int countBugs(WorkItem node) {

        int count = 0;

        if (node.type == Type.BUG) {
            count++;
        }

        for (WorkItem child : node.children) {
            count += countBugs(child);
        }

        return count;
    }

    // ==========================================
    // 9. Generate Impact Report
    // ==========================================

    public static void generateReport(WorkItem node) {

        System.out.println("\nImpact Report");

        System.out.println(
                "Stories : " + countStories(node));

        System.out.println(
                "Tasks   : " + countTasks(node));

        System.out.println(
                "Bugs    : " + countBugs(node));
    }

    // ==========================================
    // 10. Graph Impact Analysis
    //     With Cycle Detection
    // ==========================================

    public static void graphImpact(
            WorkItem node,
            Set<String> visited) {

        if (visited.contains(node.id)) {
            return;
        }

        visited.add(node.id);

        System.out.println(
                node.type + " -> " + node.id);

        for (WorkItem child : node.children) {
            graphImpact(child, visited);
        }
    }

    // ==========================================
    // Main
    // ==========================================

    public static void main(String[] args) {

        WorkItem epic =
                new WorkItem("EPIC-1", Type.EPIC);

        WorkItem project1 =
                new WorkItem("PROJ-1", Type.PROJECT);

        WorkItem project2 =
                new WorkItem("PROJ-2", Type.PROJECT);

        WorkItem story1 =
                new WorkItem("STORY-1", Type.STORY);

        WorkItem story2 =
                new WorkItem("STORY-2", Type.STORY);

        WorkItem task1 =
                new WorkItem("TASK-1", Type.TASK);

        WorkItem task2 =
                new WorkItem("TASK-2", Type.TASK);

        WorkItem bug1 =
                new WorkItem("BUG-1", Type.BUG);

        WorkItem bug2 =
                new WorkItem("BUG-2", Type.BUG);

        epic.addChild(project1);
        epic.addChild(project2);

        project1.addChild(story1);
        project1.addChild(task1);
        project1.addChild(bug1);

        project2.addChild(story2);
        project2.addChild(task2);
        project2.addChild(bug2);

        System.out.println("==============");
        System.out.println("Hierarchy");
        System.out.println("==============");

        printHierarchy(epic);

        System.out.println("\n==============");
        System.out.println("Epic Impact");
        System.out.println("==============");

        findImpact(epic);

        System.out.println("\n==============");
        System.out.println("Project Impact");
        System.out.println("==============");

        findImpact(project1);

        System.out.println("\n==============");
        System.out.println("Stories");
        System.out.println("==============");

        List<WorkItem> stories = new ArrayList<>();

        getStories(epic, stories);

        stories.forEach(
                s -> System.out.println(s.id));

        System.out.println("\n==============");
        System.out.println("Tasks");
        System.out.println("==============");

        List<WorkItem> tasks = new ArrayList<>();

        getTasks(epic, tasks);

        tasks.forEach(
                t -> System.out.println(t.id));

        System.out.println("\n==============");
        System.out.println("Bugs");
        System.out.println("==============");

        List<WorkItem> bugs = new ArrayList<>();

        getBugs(epic, bugs);

        bugs.forEach(
                b -> System.out.println(b.id));

        generateReport(epic);

        System.out.println("\n==============");
        System.out.println("Graph Traversal");
        System.out.println("==============");

        graphImpact(epic, new HashSet<>());
    }
}