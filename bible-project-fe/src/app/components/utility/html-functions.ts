export class HtmlFunctions {
  public static jumpToSection(sectionId: string, timeout = 300): void {
    setTimeout(() => {
      const targetElement = document.getElementById(sectionId);
      if (targetElement) {
        // Get the position of the target element
        const rect = targetElement.getBoundingClientRect();
        // Scroll with an offset (e.g., 100px from the top)
        window.scrollTo({
          top: rect.top + window.scrollY - 200, // Adjust the offset value as needed
          behavior: 'smooth'
        });
      }
    }, timeout)
  }

  public static copyTextToClipboard(text: string) {
    if (navigator && navigator.clipboard) {
      navigator.clipboard.writeText(text).then(() => {
        console.log('Text copied to clipboard successfully');
      }).catch((err) => {
        console.error('Failed to copy text to clipboard', err);
      });
    } else if (document.queryCommandSupported && document.queryCommandSupported('copy')) {
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
    } else {
      console.error('Clipboard API not supported and no fallback available');
    }
  }
}
