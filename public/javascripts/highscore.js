window.onload = init;

// Dynamically add user highscores and names
function init() {
    /*    const highscore = [];

        while (highscore.length < 3) {
            highscore.push(Math.floor(Math.random() * 21 * Math.floor(Math.random() * 23 + 239)))
        }

        const scoresWithNames = new Map();
        scoresWithNames.set('Steven', highscore[0]);
        scoresWithNames.set('Natasha', highscore[1]);
        scoresWithNames.set('Scott', highscore[2]);

        const sortedScores = new Map([...scoresWithNames.entries()].sort((a, b) => b[1] - a[1]));

        const score = sortedScores.keys();
        const names = sortedScores.values();

        document.getElementById("highscore-key-first").innerHTML = score.next().value;
        document.getElementById("highscore-key-second").innerHTML = score.next().value;
        document.getElementById("highscore-key-third").innerHTML = score.next().value;

        document.getElementById("highscore-value-first").innerHTML = names.next().value;
        document.getElementById("highscore-value-second").innerHTML = names.next().value;
        document.getElementById("highscore-value-third").innerHTML = names.next().value;*/
}

const timerDisplay = document.querySelector('#timer');
const startStopBtn = document.querySelector('#startStopBtn');

let startTime;
let elapsedTime = 0;
let timerInterval;

startStopBtn.addEventListener('click', () => {
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