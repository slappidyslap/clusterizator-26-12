/*function getRandomPastelColor() {
    const r = Math.floor((Math.random() * 128) + 127);
    const g = Math.floor((Math.random() * 128) + 127);
    const b = Math.floor((Math.random() * 128) + 127);
    const alpha = 0.5;
    return `rgba(${r}, ${g}, ${b}, ${alpha})`;
}*/

// Темная тема
function getRandomPastelColor() {
    const r = Math.floor((Math.random() * 64));
    const g = Math.floor((Math.random() * 64));
    const b = Math.floor((Math.random() * 64));
    const alpha = 0.2;
    return `rgba(${r}, ${g}, ${b}, ${alpha})`;
}

graph.on('selectNode', e => {
  document.getElementById("graph").style.backgroundColor = getRandomPastelColor()
  GraphView.selectEdge(e.nodes[0])
});

network.on('dragEnd', function(){
  graph.unselectAll();
});

network.on('dragEnd', function(){
  graph.unselectAll();
});