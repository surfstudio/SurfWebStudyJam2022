import { SFile } from 'entity/s-file'


export class Candidate {
  id: string = '';
  eventId: string = '';
  firstName: string = '';
  lastName: string = '';
  university: string = '';
  faculty: string = '';
  course: string = '';
  experience: string = '';
  cv?: SFile;
  vcs: string = '';
  email: string = '';
  telegram: string = '';
  feedback: string = '';
}
