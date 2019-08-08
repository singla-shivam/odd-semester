#include <bits/stdc++.h>
using namespace std;
string shiftCipher(string s, int key, int length){
  char *c = new char(length);
  int i;
  for(i = 0; i < length && s[i] != 0; i++){
    if(s[i] >= 65 && s[i] <= 90) c[i] = ((s[i] + key - 65) % 26) + 65;
    else if(s[i] >= 97 && s[i] <= 122) c[i] = ((s[i] + key - 97) % 26) + 97;
    else c[i] = s[i];
  }
  return c;
}
int main(){
  int l = 0;
  string text;
  ofstream cipheredFile;
  cout << "Enter text" << endl;
  // cin >> text;
  getline(cin, text);
  while(text[l] != 0) l++;
  string ciphered = shiftCipher(text, 5, l);
  cipheredFile.open("secret");
  cipheredFile << ciphered;
  cipheredFile.close();
  return 0 ;
}