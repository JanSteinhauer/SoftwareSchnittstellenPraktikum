

function init(){
    console.log("test")
    fetch("/game1", {
        method: "POST",
        body: JSON.stringify({name: "milk"}),
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error(error);
        });
}
init();

const img1 = document.getElementById("shovel");
const img2 = document.getElementById("scale");
const gram_txt = document.getElementById("gram");


let isDragging = false;
let currentX;
let currentY;
let initialX;
let initialY;
let xOffset = 0;
let yOffset = 0;
let gram = 0;

img1.addEventListener("mousedown", dragStart);
img1.addEventListener("mouseup", dragEnd);
img1.addEventListener("mouseout", dragEnd);
img1.addEventListener("mousemove", drag);

function dragStart(e) {
    initialX = e.clientX - xOffset;
    initialY = e.clientY - yOffset;

    isDragging = true;
}

function dragEnd(e) {
    initialX = currentX;
    initialY = currentY;

    isDragging = false;
}

function drag(e) {
    if (isDragging) {
        e.preventDefault();
        currentX = e.clientX - initialX;
        currentY = e.clientY - initialY;

        xOffset = currentX;
        yOffset = currentY;

        setTranslate(currentX, currentY, img1);
    }
}

function setTranslate(xPos, yPos, el) {
    el.style.transform = "translate3d(" + xPos + "px, " + yPos + "px, 0)";
}

function checkOverlap() {
    const img1Rect = img1.getBoundingClientRect();
    const img2Rect = img2.getBoundingClientRect();

    if (img1Rect.left < img2Rect.right &&
        img1Rect.right > img2Rect.left &&
        img1Rect.top < img2Rect.bottom &&
        img1Rect.bottom > img2Rect.top) {
        add_gram_scale()
        setTranslate(0, 0, img1);
        xOffset = 0;
        yOffset = 0;
    }
}

function add_gram_scale(){
    if(gram < 30){
        gram += 1;
        gram_txt.innerHTML = gram;
    }

    console.log("current weight: " + gram)
}

img1.addEventListener("mouseup", checkOverlap);
img1.addEventListener("mouseout", checkOverlap);


const timerDisplay = document.querySelector('#timer');
const startStopBtn = document.querySelector('#startStopBtn');

let startTime;
let elapsedTime = 0;
let timerInterval;

startStopBtn.addEventListener('click', () => {
    console.log("start Stop")
    if (!timerInterval) {
        startTime = Date.now();
        timerInterval = setInterval(updateTimer, 10);
        startStopBtn.textContent = 'Stop';
        startStopBtn.style.backgroundColor = 'red';
    } else {
        clearInterval(timerInterval);
        elapsedTime += Date.now() - startTime;
        timerInterval = null;
        startStopBtn.textContent = 'Start';
        startStopBtn.style.backgroundColor = 'yellow';
    }
});

function updateTimer() {
    const currentTime = Date.now();
    elapsedTime += currentTime - startTime;
    startTime = currentTime;
    const minutes = Math.floor(elapsedTime / 60000);
    const seconds = Math.floor((elapsedTime % 60000) / 1000);
    const milliseconds = elapsedTime % 1000;
    timerDisplay.textContent = `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}:${milliseconds.toString().padStart(3, '0')}`;
}


const siebtraeger = document.querySelector('#siebtraeger');
let isFlipped = false;

siebtraeger.addEventListener('click', () => {
    isFlipped = !isFlipped;
    siebtraeger.style = isFlipped ? 'margin-left: 305px' : 'margin-right: 305px';
    siebtraeger.style.transform = isFlipped ? 'scaleX(-1)' : 'none';
});
