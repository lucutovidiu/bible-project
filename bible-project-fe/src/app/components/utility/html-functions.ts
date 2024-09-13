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
}
