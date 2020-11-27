const eventBus = {
    async on(event, callback) {
      await  document.addEventListener(event, (e) => callback(e.detail));
    },
  async   dispatch(event, data) {
    await  document.dispatchEvent(new CustomEvent(event, { detail: data }));
    },
   async  remove(event, callback) {
    await  document.removeEventListener(event, callback);
    },
  };export default eventBus;