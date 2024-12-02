export class HtmlFunctions {
  public static jumpToSection(
    sectionId: string,
    timeout = 300,
    top: number = 100,
  ): void {
    setTimeout(() => {
      const targetElement = document.getElementById(sectionId);
      if (targetElement) {
        // Get the position of the target element
        const rect = targetElement.getBoundingClientRect();
        // Scroll with an offset (e.g., 100px from the top)
        window.scrollTo({
          top: rect.top + window.scrollY - top, // Adjust the offset value as needed
          behavior: 'smooth',
        });
      }
    }, timeout);
  }

  public static copyTextToClipboard(text: string) {
    if (navigator && navigator.clipboard) {
      navigator.clipboard
        .writeText(text)
        .then(() => {
          console.log('Text copied to clipboard successfully');
        })
        .catch((err) => {
          this.textAreaClipboardMethod(text);
          console.error('Failed to copy text to clipboard', err);
        });
    } else {
      this.textAreaClipboardMethod(text);
      console.error('Clipboard API not supported and no fallback available');
    }
  }

  private static textAreaClipboardMethod(text: string) {
    if (
      document.queryCommandSupported &&
      document.queryCommandSupported('copy')
    ) {
      const textarea = document.createElement('textarea');
      textarea.value = text;
      document.body.appendChild(textarea);
      textarea.select();
      try {
        document.execCommand('copy');
        console.log('Fallback: Text copied to clipboard');
      } catch (err) {
        console.error('Fallback: Failed to copy text to clipboard', err);
      }
      document.body.removeChild(textarea);
    }
  }
}
