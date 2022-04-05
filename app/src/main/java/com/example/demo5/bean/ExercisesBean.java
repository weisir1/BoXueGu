package com.example.demo5.bean;

public class ExercisesBean {
    public int id;//每张习题的Id
    public String title;//每章习题的标题
    public String content;//每张习题的数目
    public int background;//每张习题前边的序号背景
    public int subjectId;//每章习题的Id
    public String subject;//每道习题的题干
    public String a;//每道题的A选项
    public String b;//每道题的B选项
    public String c;//每道题的C选项
    public String d;//每道题的D选项
    public int answer;//每道题的正确答案
    //select为0表示所选项是对的，1表示所选项A是对的，2表示所选项B是对的，3表示所选项C是对的，4表示所选项D是对的
    public int select;
}
