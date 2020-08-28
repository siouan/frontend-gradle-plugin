export default function(to) {
    if (to.hash) {
        return { selector: to.hash };
    } else {
        return { x: 0, y: 0 };
    }
};
