#include <bits/stdc++.h>
using namespace std;
void deCipher(string s){
  int i,j;
  string c;
  for(j = 1 ; j < 26; j++){
    cout << j << " : : ";
    for(i = 0; i < 40; i++){
      if(s[i] >= 65 && s[i] <= 90) c[i] = ((s[i] - j - 65) % 26) + 65;
      else if(s[i] >= 97 && s[i] <= 122) c[i] = ((s[i] - j - 97) % 26) + 97;
      else c[i] = s[i];
      cout << c[i];
    }
    cout << endl;
  }
}
int main(){
  ifstream inFile;
  inFile.open("secret");
  stringstream buffer;
  buffer << inFile.rdbuf();
  string cipheredText = buffer.str();
  deCipher(cipheredText);
  inFile.close();
  return 0;
}