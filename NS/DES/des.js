function DES(message) {
    // 56 bit key - be cautious it may overflow
    const key = '10001011000100101100110100110100010101100000011110000110'
    // console.log(key.length)

    let roundKeys = []
    const ip = [58,50,42,34,26,18,10,2, 
        60,52,44,36,28,20,12,4, 
        62,54,46,38,30,22,14,6, 
        64,56,48,40,32,24,16,8, 
        57,49,41,33,25,17,9,1, 
        59,51,43,35,27,19,11,3, 
        61,53,45,37,29,21,13,5, 
        63,55,47,39,31,23,15,7
    ]

    const binaryMessage = stringToBinary(message)

    roundKeyGenerate()

    for(let i = 0, len = binaryMessage.length; i < len; i += 64) {
        const block64 = binaryMessage.slice(i, i + 64).padEnd(64, '0')
        const blockAfterIP = permute(ip, block64)

        for(let j = 0; j < 16; j ++) {
            
        }
    }

    function functionF(rightI, key) {

    }

    function permute(permutation, message) {
        var a = ''
        for(let i = 0; i < permutation.length; i++){
            a += message[permutation[i] - 1]
        }
        return a
    }

    function stringToBinary(m) {
        var a = ''
        for(let i = 0; i < m.length; i++){
            a += m.charCodeAt(i).toString(2).padStart(8, '0')
        }
        return a
    }

    function roundKeyGenerate(){
        let left = key.slice(0, 28)
        let right = key.slice(28)

        for(let j = 0; j < 16; j ++) {
            left = leftShift(left)
            right = leftShift(right)
            // hard permute
            roundKeys.push(left.slice(0, 24) + right.slice(0, 24))
            console.log(roundKeys[j])
        }
    }

    function leftShift(k) {
        let a = []
        for(let i = 0, len = k.length - 1; i < len; i ++){
            a[i] = k[i + 1]
        }
        a[k.length - 1] = k[0]
        return a.join('')
    }

    function rightShift(k) {
        let a = []
        for(let i = 1, len = k.length; i < len; i ++){
            a[i] = k[i - 1]
        }
        a[0] = k[k.length - 1]
        return a.join('')
    }
}

DES('Shivam')
