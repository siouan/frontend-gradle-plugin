export default function(to) {
    console.log("Scroll behavior works!");
    if (to.hash) {
        return { selector: to.hash };
    } else {
        return { x: 0, y: 0 };
    }
};
