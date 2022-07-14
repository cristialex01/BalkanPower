import './newtify-global-styles.js';
import '@vaadin/vaadin-lumo-styles/badge.js';

const $_documentContainer = document.createElement('template');

$_documentContainer.innerHTML = `
    <custom-style>
        <style include="
        newtify-global-styles
        lumo-badge
        "/>
    </custom-style>`;

document.head.appendChild($_documentContainer.content);