import defaultSettings from '@/settings'

const title = defaultSettings.title || 'OrderQuery'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
