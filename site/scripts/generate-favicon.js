const fs = require('fs');
const path = require('path');

// Create a simpler version of the logo for favicon
const faviconSvgContent = `
<svg viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg" width="32" height="32">
  <defs>
    <linearGradient id="faviconGrad" x1="0%" y1="0%" x2="100%" y2="100%">
      <stop offset="0%" stop-color="#a78bfa" />
      <stop offset="100%" stop-color="#7c3aed" />
    </linearGradient>
  </defs>
  
  <g transform="translate(16, 16)">
    <!-- Simplified version of the logo for favicon -->
    <path
      d="M -8 -10 Q -11 0 -8 10 Q 0 5 8 10 Q 11 0 8 -10 Q 0 -5 -8 -10"
      fill="url(#faviconGrad)"
    />
  </g>
</svg>
`.trim();

// Write the SVG favicon
const faviconSvgPath = path.join(__dirname, '../static/img/favicon.svg');
fs.writeFileSync(faviconSvgPath, faviconSvgContent);

console.log('✅ Generated favicon.svg at:', faviconSvgPath);
console.log('');
console.log('To generate the favicon.ico file, you can:');
console.log('1. Use an online SVG to ICO converter (recommended for favicon.ico)');
console.log('2. Use ImageMagick: convert -density 300 -background transparent favicon.svg -resize 32x32 favicon.ico');
console.log('3. Use Inkscape: inkscape --export-png=temp.png --export-width=32 --export-height=32 favicon.svg && convert temp.png favicon.ico');
console.log('');
console.log('For modern browsers, you can also add this to your HTML head:');
console.log('<link rel="icon" type="image/svg+xml" href="/img/favicon.svg">');
console.log('');
console.log('The generated favicon uses the same style as the logo but optimized for small sizes.');