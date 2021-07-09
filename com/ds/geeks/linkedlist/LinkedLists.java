package com.ds.geeks.linkedlist;

import java.util.Comparator;
import java.util.PriorityQueue;

public class LinkedLists {
    static Node mergeKSortedLists(Node arr[], int k) {
        Node head = null, last = null;

        // priority_queue 'pq' implemeted
        // as min heap with the
        // help of 'compare' function
        PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
            public int compare(Node a, Node b) {
                return a.data - b.data;
            }
        });

        // push the head nodes of all
        // the k lists in 'pq'
        for (int i = 0; i < k; i++)
            if (arr[i] != null)
                pq.add(arr[i]);

        // loop till 'pq' is not empty
        while (!pq.isEmpty()) {
            // get the top element of 'pq'
            Node top = pq.peek();
            pq.remove();

            // check if there is a node
            // next to the 'top' node
            // in the list of which 'top'
            // node is a member
            if (top.next != null)
                // push the next node in 'pq'
                pq.add(top.next);

            // if final merged list is empty
            if (head == null) {
                head = top;
                // points to the last node so far of
                // the final merged list
                last = top;
            } else {
                // insert 'top' at the end
                // of the merged list so far
                last.next = top;

                // update the 'last' pointer
                last = top;
            }
        }
        // head node of the required merged list
        return head;
    }

    // function to print the singly linked list
    public static void printList(Node head) {
        while (head != null) {
            System.out.print(head.data + " ");
            head = head.next;
        }
    }

    // Utility function to create a new node
    public static Node push(int data) {
        Node newNode = new Node(data);
        newNode.next = null;
        return newNode;
    }

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            next = null;
        }
    }

    static class RotateALinkedList {
        /**
         * Given a singly linked list, rotate the linked list counter-clockwise by k
         * nodes. Where k is a given positive integer. For example, if the given linked
         * list is 10->20->30->40->50->60 and k is 4, the list should be modified to
         * 50->60->10->20->30->40. Assume that k is smaller than the count of nodes in a
         * linked list.
         */
        /**
         * Method-1: To rotate the linked list, we need to change the next of kth node
         * to NULL, the next of the last node to the previous head node, and finally,
         * change the head to (k+1)th node. So we need to get hold of three nodes: kth
         * node, (k+1)th node, and last node. Traverse the list from the beginning and
         * stop at kth node. Store pointer to kth node. We can get (k+1)th node using
         * kthNode->next. Keep traversing till the end and store a pointer to the last
         * node also. Finally, change pointers as stated above.
         */
        Node head;

        void rotate(int k) {
            if (k == 0)
                return;

            // Let us understand the below code for example k = 4
            // and list = 10->20->30->40->50->60.
            Node current = head;

            // current will either point to kth or NULL after this
            // loop. current will point to node 40 in the above example
            int count = 1;
            while (count < k && current != null) {
                current = current.next;
                count++;
            }

            // If current is NULL, k is greater than or equal to count
            // of nodes in linked list. Don't change the list in this case
            if (current == null)
                return;

            // current points to kth node. Store it in a variable.
            // kthNode points to node 40 in the above example
            Node kthNode = current;

            // current will point to last node after this loop
            // current will point to node 60 in the above example
            while (current.next != null)
                current = current.next;

            // Change next of last node to previous head
            // Next of 60 is now changed to node 10

            current.next = head;

            // Change head to (k+1)th node
            // head is now changed to node 50
            head = kthNode.next;

            // change next of kth node to null
            kthNode.next = null;
        }

        // This function rotates a linked list
        // counter-clockwise and updates the
        // head. The function assumes that k is
        // smaller than size of linked list.
        void circularRotate(int k) {
            /**
             * To rotate a linked list by k, we can first make the linked list circular and
             * then moving k-1 steps forward from head node, making (k-1)th node’s next to
             * null and make kth node as head.
             */
            if (k == 0)
                return;

            // Let us understand the below
            // code for example k = 4 and
            // list = 10.20.30.40.50.60.
            Node current = head;

            // Traverse till the end.
            while (current.next != null)
                current = current.next;

            current.next = head;
            current = head;

            // traverse the linked list to k-1 position which
            // will be last element for rotated array.
            for (int i = 0; i < k - 1; i++)
                current = current.next;

            // update the head_ref and last element pointer to null
            head = current.next;
            current.next = null;
        }

        /*
         * Given a reference (pointer to pointer) to the head of a list and an int, push
         * a new node on the front of the list.
         */
        void push(int new_data) {
            /*
             * 1 & 2: Allocate the Node & Put in the data
             */
            Node new_node = new Node(new_data);

            /* 3. Make next of new Node as head */
            new_node.next = head;

            /* 4. Move the head to point to new Node */
            head = new_node;
        }

        void printList() {
            Node temp = head;
            while (temp != null) {
                System.out.print(temp.data + " ");
                temp = temp.next;
            }
            System.out.println();
        }

        /* Driver program to test above functions */
        public static void testDataRotateALinkedList()
            RotateALinkedList llist = new RotateALinkedList();

            // create a list 10->20->30->40->50->60
            for (int i = 60; i >= 10; i -= 10)
                llist.push(i);

            System.out.println("Given list");
            llist.printList();

            llist.rotate(4);

            System.out.println("Rotated Linked List");
            llist.printList();
        }

    }

    static class MoveAllOccurrencesOfNodeToEndOfLinkedList {
        /**
         * Given a linked list and a key in it, the task is to move all occurrences of
         * the given key to the end of the linked list, keeping the order of all other
         * elements the same.
         * 
         * Examples:
         * 
         * Input : 1 -> 2 -> 2 -> 4 -> 3 key = 2 Output : 1 -> 4 -> 3 -> 2 -> 2
         * 
         * Input : 6 -> 6 -> 7 -> 6 -> 3 -> 10 key = 6 Output : 7 -> 3 -> 10 -> 6 -> 6
         * -> 6
         */
        /**
         * Efficient Solution 1: is to keep two pointers: pCrawl => Pointer to traverse
         * the whole list one by one. pKey => Pointer to an occurrence of the key if a
         * key is found. Else same as pCrawl. We start both of the above pointers from
         * the head of the linked list. We move pKey only when pKey is not pointing to a
         * key. We always move pCrawl. So, when pCrawl and pKey are not the same, we
         * must have found a key that lies before pCrawl, so we swap between pCrawl and
         * pKey, and move pKey to the next location. The loop invariant is, after
         * swapping of data, all elements from pKey to pCrawl are keys.
         */
        // A Linked list Node
        static class Node {
            int data;
            Node next;
        }

        // A urility function to create a new node.
        static Node newNode(int x) {
            Node temp = new Node();
            temp.data = x;
            temp.next = null;
            return temp;
        }

        // Utility function to print the elements
        // in Linked list
        static void printList(Node head) {
            Node temp = head;
            while (temp != null) {
                System.out.printf("%d ", temp.data);
                temp = temp.next;
            }
            System.out.printf("\n");
        }

        // Moves all occurrences of given key to
        // end of linked list.
        static void moveToEnd(Node head, int key) {
            // Keeps track of locations where key
            // is present.
            Node pKey = head;

            // Traverse list
            Node pCrawl = head;
            while (pCrawl != null) {
                // If current pointer is not same as pointer
                // to a key location, then we must have found
                // a key in linked list. We swap data of pCrawl
                // and pKey and move pKey to next position.
                if (pCrawl != pKey && pCrawl.data != key) {
                    pKey.data = pCrawl.data;
                    pCrawl.data = key;
                    pKey = pKey.next;
                }

                // Find next position where key is present
                if (pKey.data != key)
                    pKey = pKey.next;

                // Moving to next Node
                pCrawl = pCrawl.next;
            }
        }

        // Function to remove key to end
        public static Node keyToEnd(Node head, int key) {

            // Node to keep pointing to tail
            Node tail = head;

            if (head == null) {
                return null;
            }

            while (tail.next != null) {
                tail = tail.next;
            }

            // Node to point to last of linked list
            Node last = tail;

            Node current = head;
            Node prev = null;

            // Node prev2 to point to previous when head.data!=key
            Node prev2 = null;

            // loop to perform operations to remove key to end
            while (current != tail) {
                if (current.data == key && prev2 == null) {
                    prev = current;
                    current = current.next;
                    head = current;
                    last.next = prev;
                    last = last.next;
                    last.next = null;
                    prev = null;
                } else {
                    if (current.data == key && prev2 != null) {
                        prev = current;
                        current = current.next;
                        prev2.next = current;
                        last.next = prev;
                        last = last.next;
                        last.next = null;
                    } else if (current != tail) {
                        prev2 = current;
                        current = current.next;
                    }
                }
            }
            return head;
        }

        // Driver code
        public static void testDataMoveAllOccurrencesOfNodeToEndOfLinkedList() {
            Node head = newNode(10);
            head.next = newNode(20);
            head.next.next = newNode(10);
            head.next.next.next = newNode(30);
            head.next.next.next.next = newNode(40);
            head.next.next.next.next.next = newNode(10);
            head.next.next.next.next.next.next = newNode(60);

            System.out.printf("Before moveToEnd(), the Linked list is\n");
            printList(head);

            int key = 10;
            moveToEnd(head, key);

            System.out.printf("\nAfter moveToEnd(), the Linked list is\n");
            printList(head);
        }
    }

    static void testDataMergeKSortedListsUsingMinHeap() {
        int k = 3; // Number of linked lists
        int n = 4; // Number of elements in each list

        // an array of pointers storing the head nodes
        // of the linked lists
        Node arr[] = new Node[k];

        arr[0] = new Node(1);
        arr[0].next = new Node(3);
        arr[0].next.next = new Node(5);
        arr[0].next.next.next = new Node(7);

        arr[1] = new Node(2);
        arr[1].next = new Node(4);
        arr[1].next.next = new Node(6);
        arr[1].next.next.next = new Node(8);

        arr[2] = new Node(0);
        arr[2].next = new Node(9);
        arr[2].next.next = new Node(10);
        arr[2].next.next.next = new Node(11);

        // Merge all lists
        Node head = mergeKSortedLists(arr, k);
        printList(head);
    }
}
