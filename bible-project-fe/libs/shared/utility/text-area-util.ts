export class TextAreaUtil {
  private charsPerLine: number = 50;

  public dynamicRowCalculator(text: string): number {
    const adjustedText = text.replace(/\n/g, ' '.repeat(this.charsPerLine)); // Each newline counts as a full line
    return Math.ceil(adjustedText.length / this.charsPerLine);
  }

  public recalculateCharsPerLine(newWindowWidth: number) {
    const width = newWindowWidth;

    // Adjust based on screen width (simple example, customize as needed)
    if (width >= 1024) {
      // Desktop: Larger screens
      this.charsPerLine = 105;
    } else if (width >= 768) {
      // Tablet: Medium screens
      this.charsPerLine = 60;
    } else if (width >= 500) {
      // Tablet: Medium screens
      this.charsPerLine = 50;
    } else if (width >= 440) {
      // Tablet: Medium screens
      this.charsPerLine = 40;
    } else {
      // Mobile: Smaller screens
      this.charsPerLine = 35;
    }
  }
}
