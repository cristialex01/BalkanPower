const container = document.createElement('template');

container.innerHTML = `
 <!--suppress CssInvalidHtmlTagReference, CssUnusedSymbol, HtmlUnknownTag, HtmlUnknownAttribute, CssUnresolvedCustomProperty -->
    <!-- INCLUDED IN GLOBAL STYLES -->
    <dom-module id="newtify-global-styles">
    <template>
            <!--suppress CssUnknownTarget -->
            <style>
            
            html {
            }
            
            /*vaadin-vertical-layout[slot="navbar"] {*/
            /*    background: red;*/
            /*    flex-direction: row;*/
            /*    align-items: center;*/
            /*}*/
            
       </style>
     </template>
  </dom-module>`;

document.head.appendChild(container.content);
