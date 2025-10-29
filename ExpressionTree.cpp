#include <iostream>
#include <stack>
#include <cctype>
using namespace std;


struct Node {
    char data;
    Node *left, *right;
    Node(char val) {
        data = val;
        left = right = NULL;
    }
};


Node* buildExpressionTree(string postfix) {
    stack<Node*> st;

    for (char ch : postfix) {
        if (isalnum(ch)) {
            st.push(new Node(ch)); 
        } else {
            
            Node* right = st.top(); st.pop();
            Node* left = st.top(); st.pop();
            Node* newNode = new Node(ch);
            newNode->left = left;
            newNode->right = right;
            st.push(newNode);
        }
    }
    return st.top();
}


void inorder(Node* root) {
    if (root) {
        if (root->left) cout << "(";
        inorder(root->left);
        cout << root->data;
        inorder(root->right);
        if (root->right) cout << ")";
    }
}

void preorder(Node* root) {
    if (root) {
        cout << root->data;
        preorder(root->left);
        preorder(root->right);
    }
}

void postorder(Node* root) {
    if (root) {
        postorder(root->left);
        postorder(root->right);
        cout << root->data;
    }
}


int evaluate(Node* root) {
    if (!root) return 0;

    
    if (isdigit(root->data))
        return root->data - '0';

   
    int leftVal = evaluate(root->left);
    int rightVal = evaluate(root->right);

    switch (root->data) {
        case '+': return leftVal + rightVal;
        case '-': return leftVal - rightVal;
        case '*': return leftVal * rightVal;
        case '/': return rightVal != 0 ? leftVal / rightVal : 0;
        default: return 0;
    }
}

int main() {
    string postfix;
    cout << "Enter Postfix Expression (digits & operators only): ";
    cin >> postfix;

    Node* root = buildExpressionTree(postfix);

    cout << "\nInorder Traversal: ";
    inorder(root);
    cout << "\nPreorder Traversal: ";
    preorder(root);
    cout << "\nPostorder Traversal: ";
    postorder(root);

    cout << "\n\nEvaluated Result: " << evaluate(root) << endl;

    return 0;
}
