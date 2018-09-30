package com.example.leetcode.normal;

/**
 * author: YETLAND
 * created on: 2018/4/18 17:21
 * description:
 */
public class Test02 {
    public static void main(String... args) {
        ListNode listNodeA1 = new ListNode(2);
        ListNode listNodeA2 = new ListNode(3);
        ListNode listNodeA3 = new ListNode(3);

        listNodeA3.next = new ListNode(9);
        listNodeA2.next = listNodeA3;
        listNodeA1.next = listNodeA2;

        ListNode listNodeB1 = new ListNode(3);
        ListNode listNodeB2 = new ListNode(3);
        ListNode listNodeB3 = new ListNode(3);

        listNodeB3.next = new ListNode(5);
        listNodeB2.next = listNodeB3;
        listNodeB1.next = listNodeB2;

//        addTwoNumbers(listNodeA1, listNodeB1);
        addTwoNumbers(new ListNode(5), new ListNode(5));


    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode listNode1 = l1;
        ListNode listNode2 = l2;
        ListNode head = null;
        ListNode current = null;
        int round = 1;
        int sum = 0, value1, value2;
        while (true) {
            if (round == 1) {
                value1 = listNode1.val;
                value2 = listNode2.val;
                sum = value1 + value2;
                head = new ListNode(sum % 10);
            } else {
                if (listNode1.next == null && listNode2.next == null) {
                    if (sum >= 10) {
                        ListNode newNode = new ListNode(sum / 10);
                        if (current == null) {
                            head.next = newNode;
                        }
                        current = head.next;
                        while (current.next != null)
                            current = current.next;//得到当前尾结点

                        current.next = newNode;
                        newNode.next = null;
                    }
                    break;
                }

                if (listNode1.next != null) {
                    listNode1 = listNode1.next;
                    value1 = listNode1.val;
                } else {
                    value1 = 0;
                }
                if (listNode2.next != null) {
                    listNode2 = listNode2.next;
                    value2 = listNode2.val;
                } else {
                    value2 = 0;
                }

                sum = value1 + value2 + (sum / 10);
                ListNode newNode = new ListNode(sum % 10);
                if (current == null) {
                    head.next = newNode;
                }
                current = head.next;

                while (current.next != null)
                    current = current.next;//得到当前尾结点

                current.next = newNode;
                newNode.next = null;

            }
            round++;
        }

        return head;
    }
}
