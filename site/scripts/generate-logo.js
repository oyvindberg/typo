const fs = require('fs');
const path = require('path');

// Create the SVG content as a string
const svgContent = `
<svg viewBox="0 0 200 200" xmlns="http://www.w3.org/2000/svg" width="200" height="200">
  <defs>
    <linearGradient id="simpleGrad" x1="0%" y1="0%" x2="100%" y2="100%">
      <stop offset="0%" stop-color="#a78bfa" />
      <stop offset="100%" stop-color="#7c3aed" />
    </linearGradient>
  </defs>
  
  <g transform="translate(100, 100)">
    <!-- Simple abstract shape - made bigger -->
    <path
      d="M -50 -60 Q -70 0 -50 60 Q 0 30 50 60 Q 70 0 50 -60 Q 0 -30 -50 -60"
      fill="url(#simpleGrad)"
    />
  </g>
</svg>
`.trim();

// Write SVG file
const svgPath = path.join(__dirname, '../static/img/logo.svg');
fs.writeFileSync(svgPath, svgContent);

console.log('✅ Generated logo.svg at:', svgPath);
console.log('You can now use this SVG file or convert it to PNG using an online converter or tool like Inkscape.');
console.log('For PNG conversion, you can:');
console.log('1. Use an online SVG to PNG converter');
console.log('2. Use Inkscape: inkscape --export-png=logo.png --export-width=512 --export-height=512 logo.svg');
console.log('3. Use ImageMagick: convert -density 300 logo.svg -resize 512x512 logo.png');