let list = ['spy', 'ray', 'spy', 'room', 'once', 'ray', 'spy', 'once'];

let testArray = [[1, 123], [2, 345]];
let arrayToMap = new Map(testArray);
//console.log(arrayToMap)

let map = new Map();


for (let i = 0; i < list.length; i++) {
    if(map.has(list[i])) {
        // 키가 이미 있을때
        map.set(list[i], map.get(list[i]) + 1);
    } else {
        map.set(list[i], 1);
    }
}

// console.log(map);
// console.log(map.entries());
// console.log(Array.isArray(map.entries()));
// console.log(...map);
// console.log(...map.entries());

let arr = [...map].sort((a, b) => a[1] - b[1]);
let arr2 = [...map].sort((a, b) => {
    if(a[1] < b[1]) {
        return -1;
    } else if(a[1] > b[1]) {
        return 1;
    } else {
        if(a[0] < b[0])
            return -1;
        else if (a[0] > b[0]) {
            return 1;
        } else {
            return 0;
        }
    }
});
let map2 = new Map(arr);
console.log(arr);
console.log(arr2);
console.log('apple' - 'tree');

let arrTest = ['banana', 'apple', 'test'];
//console.log(arrTest.sort((a, b) => a - b));

